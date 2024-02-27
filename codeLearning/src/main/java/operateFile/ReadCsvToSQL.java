package operateFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 生成SQL语句
 * <p>请使用记事本将对应的csv文件打开，另存为UTF-8格式</p>
 *
 * @author MixJade
 * @since 2023-9-22 20:24:55
 */
public class ReadCsvToSQL {
    private final static String PATH_CSV = "src/main/resources/operateFile/生成sql项目数据.csv";
    private static int nowGroupID = 160,
            nowMemberId = 872;

    private static String getGroupID() {
        return "8848" + nowGroupID++;
    }

    private static String getMembersID() {
        return "9527" + nowMemberId++;
    }


    private static String giveGroupInfo(Project project, String groupID) {
        return String.format("""
                INSERT INTO
                GROUP_INFO ( GROUP_ID, GROUP_NM, PRJ_ID)
                VALUES
                ('%s','%s','%s');
                """, groupID, project.prjName() + "组", project.prjID());
    }

    private static String giveGroupMembers(Project project, String groupNm, String groupID) {
        return String.format("""
                INSERT INTO
                GROUP_MEMBER ( MEMBER_ID, GROUP_ID, GROUP_NM, MEMBER_ROLE, USER_NO )
                VALUES
                ( '%s', '%s', '%s', '%s', '%s');
                """, getMembersID(), groupID, groupNm, project.role(), project.usrNo());
    }

    private static List<Project> readCSV() {
        String line;
        String csvSplitBy = ",";
        List<Project> projectList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ReadCsvToSQL.PATH_CSV))) {
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(csvSplitBy);
                Project project = new Project(fields[0].trim(), fields[1].trim(), fields[2].trim(), fields[3].trim());
                projectList.add(project);
            }
            projectList.remove(0); // 去掉第一行
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(new LinkedHashSet<>(projectList));
    }

    public static void main(String[] args) {
        List<Project> projectList = readCSV();
        System.out.println("路径：" + PATH_CSV);
        for (Project project : projectList) {
            String groupID = getGroupID();
            System.out.println(giveGroupInfo(project, groupID));
            String groupNm = project.prjName() + "组";
            System.out.println(giveGroupMembers(project, groupNm, groupID));
        }
    }

    /**
     * 项目信息(私有内部类)
     */
    private record Project(String prjID, String prjName, String usrNo, String role) {
    }
}
