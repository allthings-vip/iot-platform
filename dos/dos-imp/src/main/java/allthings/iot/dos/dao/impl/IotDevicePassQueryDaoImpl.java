package allthings.iot.dos.dao.impl;

import allthings.iot.dos.dao.IotDevicePassQueryDao;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * @author tyf
 * @date 2019/03/14 19:46
 */
@Repository("iotDevicePassQueryDao")
public class IotDevicePassQueryDaoImpl implements IotDevicePassQueryDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public String getPassTypeCodeByPassCode(String passCode) {

        String sql = " select idpt.pass_type_code from iot_dos_device_pass iddp, iot_dos_pass_type idpt " +
                " where idpt.iot_dos_pass_type_id = iddp.iot_dos_pass_type_id and iddp.pass_code=:passCode " +
                " and idpt.is_deleted=false and iddp.is_deleted = false ;";

        Query query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);

        query.setParameter("passCode", passCode);
        return String.valueOf(query.uniqueResult());
    }

}
