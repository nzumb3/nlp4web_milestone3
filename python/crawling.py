from multiprocessing import Pool
import pandas as pd
import numpy as np
import requests
import json
import time

API_bene = "4298852e"
API_stefan = "ccad0020"
API_nzumbe = "347094cb"
API_volker = "c4f33076"
URL = "http://www.omdbapi.com/"
DATA_PATH = "~/Documents/Git_Repositories/nlp4web_milestone3/data/data_crawl_ready.csv"

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
    return j

def process(responseList):
    startTime = time.time()    
    data = pd.read_csv(DATA_PATH)
    success, error, exceed = 0,0,0
    for response in responseList:
        if "Error" in response.keys() and response["Error"] == "Request limit reached!":
            exceed+=1
        elif "Error" in response.keys():
            error+=1
            data = insert(data, response["movieId"], response["Error"], True)
        else:
            success+=1
            data = insert(data, response["movieId"], response["Plot"])
    data.to_csv("data_crawl_ready.csv", index=False)
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
    print("########FINISHED########")

def getToCrawl(toCrawl=1000):
    data = pd.read_csv(DATA_PATH)
    data = data[data.Plot == "TODO"]
    print("ToDo: {}   |   Doing: {}".format(data.shape[0], toCrawl))
    todo = list(data.tconst.values[:toCrawl])
    out = []
    for i in range(toCrawl):
        if i % 4 == 0:
            out.append([todo[i], API_bene])
        elif i % 4 == 1:
            out.append([todo[i], API_nzumbe])
        elif i % 4 == 2:
            out.append([todo[i], API_volker])
        else:
            out.append([todo[i], API_stefan])
    return out

def insert(data, movieId, plot, isError=False):
    mask = data.tconst == movieId
    data.loc[mask, "Plot"] = plot
    data.loc[mask, "isError"] = isError
    return data

if __name__ == "__main__":
    print("#########START##########")
    startTime = time.time()
    toCrawl = getToCrawl(4*3000)
    p = Pool(16)
    result = p.map(crawl, toCrawl)
    tmp = time.time() - startTime
    tmp = np.round(tmp/60, 3)
    print("CRAWLING DURATION: {}".format(tmp))
    process(result)
