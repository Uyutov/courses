package masha.courses.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import masha.courses.models.Admin;
import masha.courses.models.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Parser {
    static public void parseJson(List<Student> students) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("D:/JavaProjects/Курсач/courses/src/main/resources/static/students-json/students.json"), students);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static public void parsePdf(Admin admin) {
        Document document = new Document();

        try {

            PdfWriter.getInstance(document,
                    new FileOutputStream(new File("D:/JavaProjects/Курсач/courses/src/main/resources/static/admin-pdf/admin.pdf")));

            document.open();

            Paragraph p = new Paragraph();
            p.add(admin.getEmail()+ "\n " + admin.getPassword() + "\n " + admin.getRole() + "\n " + admin.getAdmin_id());
            p.setAlignment(Element.ALIGN_CENTER);

            document.add(p);

            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}
