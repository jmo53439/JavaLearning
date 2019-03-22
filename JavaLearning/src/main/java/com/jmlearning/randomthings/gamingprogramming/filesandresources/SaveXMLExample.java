package com.jmlearning.randomthings.gamingprogramming.filesandresources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SaveXMLExample {

    public SaveXMLExample() {

    }

    public void createXMLFile() {

        File file = new File("sample.xml");
        PrintWriter out = null;

        try {

            out = new PrintWriter(file);
            writeXML(out);
        }
        catch(FileNotFoundException fex) {

            fex.printStackTrace();
        }
        finally {

            try {

                out.close();
                System.out.println("Wrote: " + file.getPath());
            }
            catch(Exception e) {

            }
        }
    }

    private void writeXML(PrintWriter out) {

        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        out.println("<root>");
        out.println(" <!-- This is a comment -->");
        out.println(" <empty></empty>");
        out.println(" <shorthand/>");
        out.println(" <text>I'm some text</text>");
        out.println(" <nested><child/></nested>");
        out.println(" <nested-text>I'm some text <child/></nested-text>");
        out.println(" <attributes attr1=\"value1\" attr2=\"value2\" />");
        out.println(" <special-chars> &lt; &gt; &amp; &apos;" + " &quot; </special-chars>");
        out.println(" <cdata>");
        out.println(" <![CDATA[");
        out.println("<xml><attr=\"xml inside xml\"/></xml>");
        out.println(" ]]>");
        out.println(" </cdata>");
        out.println("</root>");
    }

    public static void main(String[] args) {

        new SaveXMLExample().createXMLFile();
    }
}
