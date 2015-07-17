/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import java.io.File;
import java.io.PrintWriter;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class FileWriter {

    private String path;
    private Boolean isOk;
    private File file;
    private PrintWriter printWriter;

    
    /**
     * Constructor
     *
     * @param path String path
     */
    public FileWriter(String path) {
        this.path = path;
        try {
            this.file = new File(this.path);
            this.file.setWritable(true);
            this.printWriter = new PrintWriter(this.file);
            this.isOk = true;
        } catch (Exception e) {
            System.err.println("Fatal error: Can't create/read " + this.path + " file");
            this.isOk = false;
        }
    }

    /**
     * Is Ok FileWriter
     *
     * @return
     */
    public boolean IsOk() {
        return this.isOk;
    }

    /**
     * Write new line
     *
     * @param string New line
     */
    public void Write(String string) {
        this.printWriter.println(string);
    }

    /**
     * Close and close file
     */
    public void Close() {
        this.printWriter.close();
    }

}
