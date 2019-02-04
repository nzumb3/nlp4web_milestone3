from multiprocessing import Pool
import pandas as pd
import numpy as np
import requests
import json
import time

API_KEYS = [
    "4298852e", #bene
    "ccad0020", #stefan
    "347094cb", #nzumbe
    "c4f33076", #volker
    "a6fb65e5", #stefan2
    "7d0f877" #stefan3
]
URL = "http://www.omdbapi.com/"
DATA_PATH = "~/Documents/Git_Repositories/nlp4web_milestone3/data/data_crawl_ready.csv"
DATA_PATH_SEC = "~/Documents/Git_Repositories/nlp4web_milestone3/data/crawling_data_second_run.csv"

def crawl(tup):
    movieId = tup[0]
    API = tup[1]
    payload = {"i":movieId, "plot":"full", "apikey":API}
    r = requests.get(URL, params=payload)
    try:
        j = r.json()
    except:
        j = {"Error": "Unspecified Error"}
    j["movieId"]=movieId
    j["api"]=API
    return j

def process(responseList):
    startTime = time.time()    
    #data = pd.read_csv(DATA_PATH)
    data = pd.read_csv(DATA_PATH_SEC)
    success, error, exceed = 0,0,0
    failed_apis = []
    for response in responseList:
        if "Error" in response.keys() and response["Error"] == "Request limit reached!":
            exceed+=1
        elif "Error" in response.keys() and response["Error"] == "Invalid API key!":
            failed_apis.append(response["api"])
            error+=1
            data = insert(data, response["movieId"], response["Error"]+" KEY: "+response["api"], True)
        elif "Error" in response.keys():
            error+=1
            data = insert(data, response["movieId"], response["Error"], True)
        else:
            success+=1
            data = insert(data, response["movieId"], response["Plot"])
    #data.to_csv(DATA_PATH, index=False)
    data.to_csv(DATA_PATH_SEC, index=False)
    duration = time.time()-startTime#seconds
    duration = np.round(duration/60,3)#minutes
    total = success + error + exceed
    success = np.round(100*success/total, 2)
    error = np.round(100*error/total, 2)
    exceed = np.round(100*exceed/total,2)
    print("PROCESS DURATION: {}".format(duration))
    print("SUCCESS(%): {}".format(success))
    print("ERROR(%): {}".format(error))
    print("LIMIT(%): {}".format(exceed))
    print("TOTAL: {}".format(total))

def getToCrawl(toCrawl=1000):
    #data = pd.read_csv(DATA_PATH)
    data = pd.read_csv(DATA_PATH_SEC)
    data = data[data.Plot == "TODO"]
    print("ToDo: {}   |   Doing: {}".format(data.shape[0], toCrawl))
    todo = list(data.tconst.values[:toCrawl])
    out = []
    for i in range(min(toCrawl, len(todo))):
        if i % 6 == 0:
            out.append([todo[i], API_KEYS[0]])
        elif i % 6 == 1:
            out.append([todo[i], API_KEYS[1]])
        elif i % 6 == 2:
            out.append([todo[i], API_KEYS[2]])
        elif i % 6 == 3:
            out.append([todo[i], API_KEYS[3]])
        elif i % 6 == 4:
            out.append([todo[i], API_KEYS[4]])
        else:
            out.append([todo[i], API_KEYS[5]])
    return out

def insert(data, movieId, plot, isError=False):
    mask = data.tconst == movieId
    data.loc[mask, "Plot"] = plot
    data.loc[mask, "isError"] = isError
    return data

if __name__ == "__main__":
    print("#########START##########")
    startTime = time.time()
    toCrawl = getToCrawl(len(API_KEYS)*2000)
    p = Pool(16)
    result = p.map(crawl, toCrawl)
    tmp = time.time() - startTime
    tmp = np.round(tmp/60, 3)
    print("CRAWLING DURATION: {}".format(tmp))
    process(result)
    getToCrawl(1)
    print("########FINISHED########")
