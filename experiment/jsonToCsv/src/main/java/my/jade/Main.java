package my.jade;

import my.jade.entiy.MyLine;
import my.jade.entiy.MyNode;
import my.jade.entiy.MyPic;
import my.jade.utils.JsonUtil;

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
        // 将二者合一
        MyPic myPic = new MyPic(myNodeList, myLineList);
        // 将数据转为字符串
        String myPicStr = JsonUtil.objToStr(myPic);
        // 将字符串写入csv

    }
}