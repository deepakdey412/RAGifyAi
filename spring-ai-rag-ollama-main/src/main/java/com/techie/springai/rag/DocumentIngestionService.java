package com.techie.springai.rag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class DocumentIngestionService implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DocumentIngestionService.class);

    @Value("classpath:/pdf/rich_and_poor_love_story.pdf")
    private Resource resource;

    private final VectorStore vectorStore;

    public DocumentIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    //    @Override
//    public void run(String... args) {
//        System.out.println("DocumentIngestionService is running!"); // debug
//        TikaDocumentReader reader = new TikaDocumentReader(resource);
//        TextSplitter textSplitter = new TokenTextSplitter();
//        log.info("Ingesting PDF file");
//        vectorStore.accept(textSplitter.split(reader.read()));
//        log.info("Completed Ingesting PDF file");
//    }
    @Override
    public void run(String... args) {
        try {
            System.out.println("DocumentIngestionService started!"); // debug
            TikaDocumentReader reader = new TikaDocumentReader(resource);
            TextSplitter textSplitter = new TokenTextSplitter();
            log.info("Ingesting PDF file");
            vectorStore.accept(textSplitter.split(reader.read()));
            log.info("Completed Ingesting PDF file");
            System.out.println("Ingestion finished successfully!"); // debug
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
