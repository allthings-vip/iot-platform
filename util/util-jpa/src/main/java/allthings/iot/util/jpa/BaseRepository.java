package allthings.iot.util.jpa;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author :  sylar
 * @FileName :  BaseRepository
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepositoryImplementation<T, ID> {
}
