package backyard;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface StudentsMapper {
    List<StudentsMessage> selectAll();

    List<StudentsMessage> selectById(int id);

    List<StudentsMessage> selectBySociety(String societyName);

    List<RankShow> selectByTwoSixty(@Param("englishGrade") int englishGrade, @Param("mathGrade") int mathGrade);

    List<RankShow> selectDimObject(RankShow rankShow);

    List<RankShow> selectDimMap(Map map);

    List<RankShow> selectLack(RankShow rankShow);

    List<RankShow> selectChoice(RankShow rankShow);

    int addOrigin(StudentsMessage studentsMessage);

    int updateOrigin(StudentsMessage studentsMessage);

    int updateVaried(StudentsMessage studentsMessage);

    void deleteOrigin(String studentName);

    void deleteGroup(@Param("nameGroup") String[] nameGroup);

    @Select("select * from students s1,societys s2 where s1.societyId=s2.societyId and s1.id=#{id};")
    List<StudentsMessage> selectByExplain(int id);

    @Select("select * from students s1,societys s2 where s1.societyId=s2.societyId and s2.societyName=#{societyName};")
    List<StudentsMessage> selectByExplainSociety(String societyName);

}
