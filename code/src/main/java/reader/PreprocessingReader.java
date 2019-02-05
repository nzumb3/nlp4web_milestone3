package reader;

import de.tudarmstadt.ukp.dkpro.core.api.io.JCasResourceCollectionReader_ImplBase;
import org.apache.uima.UimaContext;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import types.Genre;
import types.Title;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.io.IOUtils.closeQuietly;

public class PreprocessingReader extends JCasResourceCollectionReader_ImplBase {

    private int count;

    private static final DecimalFormat percentageFormat = new DecimalFormat("##.##%");

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
        super.initialize(context);
        count = 0;
    }

    @Override
    public void getNext(JCas jCas) throws IOException {
        Resource res = nextFile();

        count++;

        initCas(jCas, res);

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(res.getInputStream(), "UTF-8"));
            convert(jCas, reader);
        }
        finally {
            closeQuietly(reader);
        }
        /*double percent = (count / 24857.0) * 100;
        System.out.printf("Document " + count + "/24857 => %.2f%%\r", percent);*/

        System.out.println("Document " + count + "/24857 => " + percentageFormat.format(count / 24857.0));
    }

    private void convert(JCas jCas, BufferedReader reader) throws IOException {
        Data data = readData(reader);
        jCas.setDocumentText(data.plot);
        jCas.setDocumentLanguage("en");
        Title title = new Title(jCas);
        title.setBegin(0);
        title.setEnd(data.plot.length());
        title.setTitle(data.title);
        title.addToIndexes();
        data.genres.forEach((genre) -> {
            Genre genreAnnotation = new Genre(jCas);
            genreAnnotation.setBegin(0);
            genreAnnotation.setEnd(data.plot.length());
            genreAnnotation.setGenre(genre);
            genreAnnotation.addToIndexes();
        });
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
