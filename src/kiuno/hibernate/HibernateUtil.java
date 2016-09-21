package kiuno.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	static {
		try {
			/*****************************************************************************/
			//�Q��xml�Ӱt�m���Ahibernate.cfg.xml�MCompany.hbm.xml��msrc�ؿ��U
			/*Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
			sessionFactory = cfg.buildSessionFactory();*/
			
			//sessionFactory = new Configuration().configure("/config/db.cfg.xml").buildSessionFactory(); //�i���t���ɮ׸��|�M�W��
			sessionFactory = new Configuration().configure().buildSessionFactory(); //�w�]����ɮ�hibernate.cfg.xml�A�i���g�ɮצW��
			/*****************************************************************************/
			
			/*****************************************************************************/
			//�Q��property�Ӱt�m���Ahibernate.properties��msrc�ؿ��U�ACompany.hbm.xml��m�b�ӬM�g��java�P��m
			/*Configuration cfg = new Configuration().addClass(kiuno.hibernate.Company.class);
			sessionFactory = cfg.buildSessionFactory();*/
			/*****************************************************************************/
			
			/*****************************************************************************/
			//�Q��Annotations�Ӱt�m�A�i�H�ϥμзǪ�JPA�H���o�PJPA/EJB 3.0���̤j�ۮe��
			//slf4j.jar�w�g���¡A���slf4j.api-1.6.1.jar
			//sessionFactory = new AnnotationConfiguration().configure("hibernateAnno.cfg.xml").buildSessionFactory();
			//PS:�ϥΤ��e�n�NCompany���אּCompanyAnnotation
			/*****************************************************************************/
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		getSessionFactory().close();
	}
}
