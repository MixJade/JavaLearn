package com.forge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forge.dto.NameDto;
import com.forge.dto.PetDto;
import com.forge.entity.Pet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 宠物信息表，外键用户表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2022-12-22
 */
@Mapper
public interface PetMapper extends BaseMapper<Pet> {
    boolean deleteId(@Param("delDate") String delDate, @Param("petId") long petId);

    boolean deleteIdGroup(@Param("delDate") String delDate, @Param("idGroup") long[] idGroup);

    int selectPetCount(@Param("petName") String petName, @Param("clientName") String clientName);

    List<PetDto> selectPetPage(@Param("petName") String petName, @Param("clientName") String clientName, @Param("begin") int begin, @Param("pageItem") int pageItem);
    List<PetDto> selectFour();
    @Select("SELECT pet_id as roleId, pet_name as roleName FROM pet WHERE is_del = '0'")
    List<NameDto> selectName();

    @Select("SELECT pet_id as roleId, pet_name as roleName FROM pet WHERE is_del = '0' AND client_id=#{clientId}")
    List<NameDto> selectByClient(long clientId);

    @Select("SELECT pet_id as roleId, pet_name as roleName FROM pet WHERE is_del = '0' AND isNull(client_id)")
    List<NameDto> selectNoClient();
    boolean updatePet(Pet pet);

    @Update("update pet set client_id=#{clientId} where pet_id=#{petId}")
    void setPetClient(@Param("clientId") long clientId, @Param("petId") long petId);

}
