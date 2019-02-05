package writer;

import de.tudarmstadt.ukp.dkpro.core.api.io.JCasFileWriter_ImplBase;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Lemma;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.NGram;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Stem;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import types.Genre;
import types.Title;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import java.io.IOException;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class PreprocessedDataWriter extends JCasFileWriter_ImplBase {

    @Override
    public void process(JCas jCas) {
        String title = JCasUtil.selectSingle(jCas, Title.class).getTitle();
        String plot = jCas.getDocumentText();
        Stream<String> genres = JCasUtil.selectCovered(jCas, Genre.class, 0, jCas.size()).stream().map(Genre::getGenre);
        Stream<String> stems = JCasUtil.selectCovered(jCas, Stem.class, 0, jCas.size()).stream().map(Stem::getValue);
        Stream<String> lemmas = JCasUtil.selectCovered(jCas, Lemma.class, 0, jCas.size()).stream().map(Lemma::getValue);
        Stream<String> namedEntities = JCasUtil.selectCovered(jCas, NamedEntity.class, 0, jCas.size()).stream().map(NamedEntity::getValue);
        Supplier<Stream<String>> nGrams = () -> JCasUtil.selectCovered(jCas, NGram.class, 0, jCas.size()).stream().map(NGram::getText);
        Stream<String> threeGrams = nGrams.get().filter((nGram) -> nGram.split(" ").length == 3);
        Stream<String> twoGrams = nGrams.get().filter((nGram) -> nGram.split(" ").length == 2);

        try {
            NamedOutputStream stream = getOutputStream(jCas, ".json");
            JsonGenerator generator = Json.createGenerator(stream);
            generator
                    .writeStartObject()
                        .write("title", title)
                        .write("plot", plot)
                        .writeStartArray("genres");
                            genres.forEach(generator::write);
                        generator.writeEnd()
                        .writeStartArray("stems");
                            stems.forEach(generator::write);
                        generator.writeEnd()
                         .writeStartArray("lemmas");
                            lemmas.forEach(generator::write);
                         generator.writeEnd()
                        .writeStartArray("named_entities");
                            namedEntities.forEach(generator::write);
                        generator.writeEnd()
                        .writeStartArray("3grams");
                            threeGrams.forEach(generator::write);
                        generator.writeEnd()
                        .writeStartArray("2grams");
                            twoGrams.forEach(generator::write);
                        generator.writeEnd()
                    .writeEnd();
            generator.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
