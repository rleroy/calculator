package com.effisoft.kata.calculator.infra.spi;

import com.effisoft.kata.calculator.domain.shell.CalculatorStorage;
import com.effisoft.kata.calculator.infra.spi.generated.Operation;
import com.effisoft.kata.calculator.infra.spi.generated.ObjectFactory;
import com.effisoft.kata.calculator.infra.spi.generated.Storage;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Paths;
import java.util.Optional;

public class XmlStorage implements CalculatorStorage {

    private static Logger logger = Logger.getLogger(XmlStorage.class);

    private JAXBContext jaxbContext = JAXBContext.newInstance("com.effisoft.kata.infra.spi.generated");
    private ObjectFactory objFactory = new ObjectFactory();
    private File datafile = Paths.get("storage.xml").toFile();

    private Storage storage;

    public XmlStorage() throws JAXBException, IOException {
        if (!datafile.exists()) {
            if (!datafile.createNewFile()) {
                throw new IOException("Could not create file " + datafile.getAbsolutePath() + "!");
            }
            storage = objFactory.createStorage();
        } else {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            storage = (Storage) unmarshaller.unmarshal(datafile);
        }
    }

    @Override
    public void store(String input, String output) {
        Operation operation = objFactory.createOperation();
        operation.setInput(input);
        operation.setOutput(output);
        storage.getOperation().add(operation);

        try {
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(storage, datafile);
        } catch (JAXBException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public Optional<String> retrieve(String input) {
        return storage.getOperation().stream()
            .filter(operation -> input.equals(operation.getInput()))
            .map(Operation::getOutput)
            .findFirst();
    }
}
