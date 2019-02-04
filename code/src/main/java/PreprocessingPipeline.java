import de.tudarmstadt.ukp.dkpro.core.ngrams.NGramAnnotator;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.*;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;
import reader.PreprocessingReader;
import writer.DummyWriter;

import java.io.IOException;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

public class PreprocessingPipeline {

    public static void main(String[] args) throws UIMAException, IOException {
        CollectionReader reader = createReader(PreprocessingReader.class, PreprocessingReader.PARAM_SOURCE_LOCATION, "src/main/resources/train/*.txt");
        AnalysisEngine segmenter = createEngine(StanfordSegmenter.class);
        AnalysisEngine posTagger = createEngine(StanfordPosTagger.class);
        AnalysisEngine lemmatizer = createEngine(StanfordLemmatizer.class);
        AnalysisEngine parser = createEngine(StanfordParser.class);
        AnalysisEngine namedEntityRecognizer = createEngine(StanfordNamedEntityRecognizer.class);
        AnalysisEngine nGramAnnotator = createEngine(NGramAnnotator.class);

        AnalysisEngine writer = createEngine(DummyWriter.class);

        SimplePipeline.runPipeline(reader, writer);
    }
}
