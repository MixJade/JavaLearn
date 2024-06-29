package my.jade;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import my.jade.entiy.MyLine;
import my.jade.entiy.MyNode;
import my.jade.entiy.MyPic;
import my.jade.utils.JsonUtil;

import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        // 设置节点列表
        List<MyNode> myNodeList = new ArrayList<>();
        myNodeList.add(MyNode.newStar("开始节点"));
        myNodeList.add(MyNode.newWork("待办事项1"));
        myNodeList.add(MyNode.newWork("待办事项2"));
        myNodeList.add(MyNode.newWork("待办事项3"));
        myNodeList.add(MyNode.newWork("待办事项4"));
        myNodeList.add(MyNode.newWork("待办事项5"));
        // 设置连线列表
        List<MyLine> myLineList = new ArrayList<>();
        myLineList.add(MyLine.bu(myNodeList.get(0).getNodeId(), myNodeList.get(1).getNodeId()));
        myLineList.add(MyLine.bu(myNodeList.get(1).getNodeId(), myNodeList.get(2).getNodeId()));
        myLineList.add(MyLine.bu(myNodeList.get(1).getNodeId(), myNodeList.get(3).getNodeId()));
        myLineList.add(MyLine.bu(myNodeList.get(3).getNodeId(), myNodeList.get(4).getNodeId()));
        myLineList.add(MyLine.bu(myNodeList.get(4).getNodeId(), myNodeList.get(5).getNodeId()));
        // 将数据转为字符串
        MyPic myPic = new MyPic(myNodeList, myLineList);
        String myPicStr = JsonUtil.objToStr(myPic);
        System.out.println(myPicStr);
        // 将字符串写入csv
        String[] header = {"index", "MyPic"};
        try {
            Writer writer = Files.newBufferedWriter(Paths.get("output/main.csv"));
            try (CSVWriter csvWriter = new CSVWriter(writer)) {
                csvWriter.writeNext(header); //写入标题
                String[] record1 = {"1", myPicStr};
                csvWriter.writeNext(record1); //写入第一行
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 从csv读取字符串
        try {
            CSVReader reader = new CSVReader(new FileReader("output/main.csv"));
            String[] nextLine;
            reader.skip(1); // 跳过第一行
            while ((nextLine = reader.readNext()) != null) {
                System.out.println(nextLine[0]);
                MyPic myPic1 = JsonUtil.strToObj(nextLine[1], MyPic.class);
                System.out.println(myPic1);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}