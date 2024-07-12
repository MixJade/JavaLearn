package genSql;

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
    private final static String PATH_CSV = "src/main/resources/genSql/生成sql项目数据.csv";
    private static int nowGroupID = 160,
            nowMemberId = 872;

    private static String getGroupID() {
        return "8848" + nowGroupID++;
    }

    private static String getMembersID() {
        return "9527" + nowMemberId++;
    }

    public static void main(String[] args) {
        (new ReadCsvToSQL()).begin();
    }

    private String giveGroupInfo(ProjectInfo project, String groupID) {
        return String.format("""
                INSERT INTO
                GROUP_INFO ( GROUP_ID, GROUP_NM, PRJ_ID)
                VALUES
                ('%s','%s','%s');
                """, groupID, project.prjName() + "组", project.prjID());
    }

    private String giveGroupMembers(ProjectInfo project, String groupNm, String groupID) {
        return String.format("""
                INSERT INTO
                GROUP_MEMBER ( MEMBER_ID, GROUP_ID, GROUP_NM, MEMBER_ROLE, USER_NO )
                VALUES
                ( '%s', '%s', '%s', '%s', '%s');
                """, getMembersID(), groupID, groupNm, project.role(), project.usrNo());
    }

    private List<ProjectInfo> readCSV() {
        String line;
        String csvSplitBy = ",";
        List<ProjectInfo> projectList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PATH_CSV))) {
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(csvSplitBy);
                ProjectInfo project = new ProjectInfo(fields[0].trim(), fields[1].trim(), fields[2].trim(), fields[3].trim());
                projectList.add(project);
            }
            projectList.remove(0); // 去掉第一行
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(new LinkedHashSet<>(projectList));
    }

    private void begin() {
        List<ProjectInfo> projectList = readCSV();
        for (ProjectInfo project : projectList) {
            String groupID = getGroupID();
            System.out.println(giveGroupInfo(project, groupID));
            String groupNm = project.prjName() + "组";
            System.out.println(giveGroupMembers(project, groupNm, groupID));
        }
    }

}

/**
 * 项目信息
 */
record ProjectInfo(String prjID, String prjName, String usrNo, String role) {
}
