package server.model.dao;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import server.model.Student;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class StudentFileSerDes {

    private static final String FILE_PATH = "src\\main\\resources\\Archive.xml";

    private static final XmlMapper xmlMapper = new XmlMapper();

    public static ArrayList<Student> deserialize() throws IOException {
        return new ArrayList<>(Arrays.asList(xmlMapper.readValue(new File(FILE_PATH), Student[].class)));
    }

    public static void serialize(ArrayList<Student> students) throws IOException {
        xmlMapper.writeValue(new File(FILE_PATH), students);
    }
}
