package attempt03;

import backyard.RankShow;
import backyard.SqlUtil;
import backyard.StudentsMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MybatisFirthDemo {
    /**
     * 用Map封装参数;
     * 只改接口处的参数
     */
    public void queryDimMap(String studentName, int englishGrade, int mathGrade) {
        studentName = "%" + studentName + "%";
        // 封装对象，这是用Map封装，要跟参数名对应上
        Map map = new HashMap();
        map.put("studentName", studentName);
        map.put("englishGrade", englishGrade);
        map.put("mathGrade", mathGrade);
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        List<RankShow> studentsMessages = mapper.selectDimMap(map);
        System.out.println(studentsMessages);
        session.close();
    }
}
