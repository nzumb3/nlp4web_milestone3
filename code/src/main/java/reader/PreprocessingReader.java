package reader;

import de.tudarmstadt.ukp.dkpro.core.api.io.JCasResourceCollectionReader_ImplBase;
import org.apache.uima.UimaContext;
import org.apache.uima.fit.factory.JCasBuilder;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import types.Title;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.io.IOUtils.closeQuietly;

public class PreprocessingReader extends JCasResourceCollectionReader_ImplBase {

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
        super.initialize(context);
    }

    @Override
    public void getNext(JCas jCas) throws IOException {
        Resource res = nextFile();

        initCas(jCas, res);

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(res.getInputStream(), "UTF-8"));
            convert(jCas, reader);
        }
        finally {
            closeQuietly(reader);
        }
    }

    private void convert(JCas jCas, BufferedReader reader) throws IOException {
        JCasBuilder builder = new JCasBuilder(jCas);

        Data data = readData(reader);
        jCas.setDocumentText(data.plot);
        Title title = new Title(jCas);
        title.setBegin(0);
        title.setEnd(data.plot.length());
        title.setTitle(data.title);
        title.addToIndexes();
    }

    private Data readData(BufferedReader reader) throws IOException {
        String title = reader.readLine();
        reader.readLine();
        String plot = reader.readLine();
        reader.readLine();
        String genreString = reader.readLine();
        String[] temp = genreString.split(",");
        String[] genres = Arrays.copyOfRange(temp, 0, temp.length - 1);
        return new Data(title, plot, genres);
    }

    private class Data {
        private String title;

        private String plot;

        private List<String> genres;

        Data(String title, String plot, String ... genres) {
            this.title = title;
            this.plot = plot;
            this.genres = Arrays.asList(genres);
        }
    }

    //private void
}
