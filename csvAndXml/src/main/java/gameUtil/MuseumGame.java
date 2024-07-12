package gameUtil;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.*;

/**
 * 判断大小的枚举
 *
 * @since 2023年6月18日 21:32
 */
enum NeedEnum {
    TIME_PEO, TIME_SCI,
    SCI_TIME, SCI_PEO,
    PEO_TIME, PEO_SCI
}

/**
 * 崩坏：星穹铁道的一个小计算工具
 *
 * @since 2023年6月18日 21:32
 */
public class MuseumGame {
    public static void main(String[] args) {
        // 计算最佳的角色配比
        MuseumGame museumGame = new MuseumGame();
        List<MuseumArea> museumArea = museumGame.getMuseumArea();
        List<MuseumPeople> museumPeople = museumGame.getMuseumPeople();
        assert museumArea != null;
        List<MuseumAreaDeletion> areaDeletionList = museumGame.whatNeed(museumArea);
        museumGame.peopleNeed(areaDeletionList, museumPeople);
    }

    /**
     * 通过Dom4j解析xml，获取博物馆区域对象
     *
     * @return 博物馆的区域数据
     */
    private List<MuseumArea> getMuseumArea() {
        SAXReader saxreader = new SAXReader();
        Document dom;
        try {
            // 不使用相对路径了,使用相当于Resource文件夹的路径
            dom = saxreader.read(getClass().getResourceAsStream("MuseumArea.xml"));
        } catch (Exception e) {
            System.out.println("XML不见了" + e);
            return null;
        }
        // 获取根节点
        assert dom != null;
        Element rootEle = dom.getRootElement();
        List<Element> areaManager = rootEle.elements("AreaManager");
        // 返回列表对象
        List<MuseumArea> museumAreaList = new ArrayList<>();
        for (Element e : areaManager) {
            MuseumArea museumArea = new MuseumArea();
            museumArea.areaName = e.elementText("AreaName");
            museumArea.needTime = Integer.parseInt(e.elementText("NeedTime"));
            museumArea.needSci = Integer.parseInt(e.elementText("NeedSci"));
            museumArea.needPeople = Integer.parseInt(e.elementText("NeedPeople"));
            museumArea.nowTime = Integer.parseInt(e.elementText("NowTime"));
            museumArea.nowSci = Integer.parseInt(e.elementText("NowSci"));
            museumArea.nowPeople = Integer.parseInt(e.elementText("NowPeople"));
            museumAreaList.add(museumArea);
        }
        return museumAreaList;
    }

    /**
     * 通过Dom4j解析xml，获取博物馆助理对象
     *
     * @return 博物馆的区域数据
     */
    private List<MuseumPeople> getMuseumPeople() {
        SAXReader saxreader = new SAXReader();
        Document dom;
        try {
            dom = saxreader.read(getClass().getResourceAsStream("MuseumPeople.xml"));
        } catch (Exception e) {
            System.out.println("XML不见了" + e);
            return null;
        }
        // 获取根节点
        assert dom != null;
        Element rootEle = dom.getRootElement();
        List<Element> areaManager = rootEle.elements("PeopleManager");
        // 返回列表对象
        List<MuseumPeople> museumPeopleList = new ArrayList<>();
        for (Element e : areaManager) {
            MuseumPeople museumPeople = new MuseumPeople();
            museumPeople.name = e.elementText("Name");
            museumPeople.time = Integer.parseInt(e.elementText("Time"));
            museumPeople.sci = Integer.parseInt(e.elementText("Sci"));
            museumPeople.people = Integer.parseInt(e.elementText("People"));
            museumPeopleList.add(museumPeople);
        }
        return museumPeopleList;
    }

    /**
     * 处理博物馆的区域数据
     *
     * @param museumAreaList 博物馆区域数据
     */
    private List<MuseumAreaDeletion> whatNeed(List<MuseumArea> museumAreaList) {
        List<MuseumAreaDeletion> areaDeletionList = new ArrayList<>();
        for (MuseumArea museumArea : museumAreaList) {
            MuseumAreaDeletion areaDeletion = new MuseumAreaDeletion();
            areaDeletion.name = museumArea.areaName;
            areaDeletion.time = museumArea.needTime - museumArea.nowTime;
            areaDeletion.sci = museumArea.needSci - museumArea.nowSci;
            areaDeletion.people = museumArea.needPeople - museumArea.nowPeople;
            areaDeletionList.add(areaDeletion);
        }
        return areaDeletionList;
    }

    /**
     * 开始计算所需人员
     */
    private void peopleNeed(List<MuseumAreaDeletion> areaDeletions, List<MuseumPeople> peopleList) {
        // 先按照缺口进行排序
        Collections.sort(areaDeletions);
        Map<String, List<String>> nedPeople = new HashMap<>();
        List<String> numS = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (MuseumAreaDeletion de : areaDeletions) {
                if (i == 0) nedPeople.put(de.name, new ArrayList<>());
                // 判断哪一部分缺失最多
                NeedEnum needEnum = bigInThree(de.time, de.sci, de.people);
                // 对人员进行排序
                List<MuseumPeople> nowPeople = sortPeople(peopleList, needEnum, de);
                // 已获取人员，进行赋值
                MuseumPeople people = nowPeople.get(0);
                de.time -= people.time;
                de.sci -= people.sci;
                de.people -= people.people;
                peopleList.remove(people);
                nedPeople.get(de.name).add(people.name);
                if (i == 2) {
                    if (de.noS()) numS.add("【" + de.name + "】未完成");
                    nedPeople.get(de.name).add(de.toString());
                }
            }
        }
        // 输出人员配置与剩余数字
        for (Map.Entry<String, List<String>> entry : nedPeople.entrySet()) {
            System.out.println(entry);
        }
        // 判断是否全部通过
        if (numS.size() > 0) {
            System.out.println(numS);
        } else System.out.println("所有区域均已配置");
    }

    /**
     * 获取三个数中最大的数
     */
    private NeedEnum bigInThree(int time, int sci, int people) {
        return time > sci ?
                (time > people ?
                        (people > sci ?
                                NeedEnum.TIME_PEO :
                                NeedEnum.TIME_SCI) :
                        NeedEnum.PEO_TIME) :
                (sci > people ?
                        (people > time ?
                                NeedEnum.SCI_PEO :
                                NeedEnum.SCI_TIME) :
                        NeedEnum.PEO_SCI);
    }

    /**
     * 对人员列表进行排序
     */
    private List<MuseumPeople> sortPeople(List<MuseumPeople> peopleList, NeedEnum needEnum, MuseumAreaDeletion de) {
        // 默认是升序排序
        peopleList.sort(Comparator.comparing((MuseumPeople i) -> getSortNum(i, needEnum, de)));
        // 升序排序改降序
        Collections.reverse(peopleList);
        return peopleList;
    }

    /**
     * 生成排序所需的数字
     *
     * @param i        人员的信息
     * @param needEnum 首要条件与次要条件
     * @param d        区域需要的数据，生成权重所需
     * @return 用于排序的数字
     */
    private int getSortNum(MuseumPeople i, NeedEnum needEnum, MuseumAreaDeletion d) {
        int sortNum;
        switch (needEnum) {
            case TIME_SCI -> sortNum = (i.time * d.time + i.sci * d.sci) / (d.time + d.sci);
            case TIME_PEO -> sortNum = (i.time * d.time + i.people * d.people) / (d.time + d.people);
            case SCI_TIME -> sortNum = (i.sci * d.sci + i.time * d.time) / (d.sci + d.time);
            case SCI_PEO -> sortNum = (i.sci * d.sci + i.people * d.people) / (d.sci + d.people);
            case PEO_TIME -> sortNum = (i.people * d.people + i.time * d.time) / (d.people + d.time);
            default -> sortNum = (i.people * d.people + i.sci * d.sci) / (d.people + d.sci);
        }
        return sortNum;
    }
}

/**
 * 博物馆的区域数据
 */
class MuseumArea {
    String areaName;
    int needTime, needSci, needPeople, nowTime, nowSci, nowPeople;
}

/**
 * 博物馆的助理数据
 */
class MuseumPeople {
    String name;
    int time, sci, people;
}

/**
 * 博物馆区域的缺失值
 */
class MuseumAreaDeletion implements Comparable<MuseumAreaDeletion> {
    String name;
    int time, sci, people;

    /**
     * 判断是否已经全部满足
     *
     * @return 未满足
     */
    boolean noS() {
        return time > 0 || sci > 0 || people > 0;
    }

    @Override
    public String toString() {
        return "剩余：{T=" + time +
                ", S=" + sci +
                ", P=" + people +
                '}';
    }

    @Override
    public int compareTo(MuseumAreaDeletion o) {
        return o.time + o.sci + o.people;
    }
}