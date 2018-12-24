package com.vince.tools.messagedemo.dao;


import com.vince.tools.messagedemo.entity.MessageSmsInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vince
 * @version 1.4
 */
@Transactional
@Repository()
public interface MessageSmsInfoDao extends JpaRepository<MessageSmsInfoEntity,Long>{

    // 这里可使用hql查询,后续完善...

    // 使用sql查询（后续优化）
    @Query(value = "select * from message_sms_info where is_del = 0 and serial_number = ?1 LIMIT ?2,?3", nativeQuery = true)
    List<MessageSmsInfoEntity> findSmsListBySerialNumber(String serialumber,Integer from,Integer size);

    @Query(value = "select count(id) from message_sms_info where is_del = 0 and serial_number = ?", nativeQuery = true)
    Integer findSmsListBySerialNumberTotal(String serialumber);

    @Query(value = "select id from message_sms_info where is_del = 0 and send_status = 1 and serial_number = ?1 and phone_num = ?2 LIMIT 1", nativeQuery = true)
    Long findMessageSmsInfoIdBySerialNumberAnd(String serialumber,String phoneNum);

    @Modifying
    @Query(value = "INSERT INTO message_sms_info (`creator`,`updater`) VALUES (?1,?2)", nativeQuery = true)
    Integer insert(String name, String password);

    @Modifying
    @Query(value = "update message_sms_info set updater = :updater,send_status = :sendStatus,err_code = :errCode,template_code = :templateCode," +
            "content = :content,send_date = :sendDate,receive_date = :receiveDate,biz_id = :bizId where id = :id",nativeQuery = true)
    Integer updateMessageSmsInfoEntity(@Param("id") Long id, @Param("updater") Integer updater, @Param("sendStatus") Integer sendStatus, @Param("errCode") String errCode
            , @Param("templateCode") String templateCode, @Param("content") String content, @Param("sendDate") String sendDate, @Param("receiveDate") String receiveDate,@Param("bizId") String bizId);


}
