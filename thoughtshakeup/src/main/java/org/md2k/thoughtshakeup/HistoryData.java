package org.md2k.thoughtshakeup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Copyright (c) 2015, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 * <p/>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <p/>
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * <p/>
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class HistoryData{
    private ArrayList<DataPoint> datapoints;
    private static HistoryData instance=null;
    public static HistoryData getInstance(){
        if(instance==null)
            instance=new HistoryData();
        return instance;
    }
    public void add(long timestamp,String thought, String rephrase, boolean favorites){
        datapoints.add(new DataPoint(timestamp,thought,rephrase,favorites));
        write();
    }
    public void update(long timestamp,boolean favorites){
        for(int i=0;i<datapoints.size();i++){
            if(datapoints.get(i).timestamp==timestamp)
                datapoints.get(i).favorites=favorites;
        }
        write();
    }
    public ArrayList<DataPoint> get(){
        return datapoints;
    }
    public ArrayList<DataPoint> get(boolean favorites){
        ArrayList<DataPoint> result=new ArrayList<>();
        for(int i=0;i<datapoints.size();i++)
            if(datapoints.get(i).favorites==favorites)
                result.add(datapoints.get(i));
        return result;
    }
    public ArrayList<DataPoint> get(long timestamp){
        ArrayList<DataPoint> result=new ArrayList<>();
        for(int i=0;i<datapoints.size();i++)
            if(datapoints.get(i).timestamp==timestamp)
                result.add(datapoints.get(i));
        return result;
    }

    private HistoryData(){
        datapoints=new ArrayList<>();
        createDirectoryIfNotExist();
        createFileIfNotExist();
        read();
    }
    private void createFileIfNotExist(){
        File file = new File(Constants.DIR_FILENAME);
        if(!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    private void createDirectoryIfNotExist() {
        File file = new File(Constants.DIRECTORY);
        if(!file.exists())
            file.mkdirs();
    }
    public void clear(){
        File file = new File(Constants.DIR_FILENAME);
        if(file.exists())
        file.delete();
    }
    public void write(){
        try {
            File file = new File(Constants.DIR_FILENAME);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i=0;i<datapoints.size();i++) {

                String str=String.valueOf(datapoints.get(i).timestamp);
                str+=",";
                str=str+datapoints.get(i).thought;
                str+=",";
                str=str+datapoints.get(i).rephrase;
                str+=",";
                str=str+String.valueOf(datapoints.get(i).favorites);
                str=str+"\n";
                bw.write(str);
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void read(){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(Constants.DIR_FILENAME));
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                DataPoint dataPoint=new DataPoint(Long.valueOf(row[0]),row[1],row[2],Boolean.valueOf(row[3]));
                datapoints.add(dataPoint);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
    }
    class DataPoint implements Serializable{
        long timestamp;
        String thought;
        String rephrase;
        boolean favorites;
        DataPoint(long timestamp, String thought, String rephrase, boolean favorites){
            this.timestamp=timestamp;this.thought=thought;this.rephrase=rephrase;this.favorites=favorites;
        }
    }
}
