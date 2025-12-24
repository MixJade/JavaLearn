package tableTes;

import java.util.ArrayList;
import java.util.List;

record MyData(String name, String age, boolean sex) {
    static List<MyData> getMyDataList() {
        ArrayList<MyData> myDataList = new ArrayList<>();
        myDataList.add(new MyData("张三", "23", true));
        myDataList.add(new MyData("李四", "34", true));
        myDataList.add(new MyData("亢龙星君", "323", false));
        myDataList.add(new MyData("王五", "55", true));
        myDataList.add(new MyData("老六", "66", false));
        return myDataList;
    }
}
