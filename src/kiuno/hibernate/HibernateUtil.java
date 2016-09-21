package kiuno.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	static {
		try {
			/*****************************************************************************/
			//利用xml來配置文件，hibernate.cfg.xml和Company.hbm.xml放置src目錄下
			/*Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
			sessionFactory = cfg.buildSessionFactory();*/
			
			//sessionFactory = new Configuration().configure("/config/db.cfg.xml").buildSessionFactory(); //可更改配至檔案路徑和名稱
			sessionFactory = new Configuration().configure().buildSessionFactory(); //預設抓取檔案hibernate.cfg.xml，可不寫檔案名稱
			/*****************************************************************************/
			
			/*****************************************************************************/
			//利用property來配置文件，hibernate.properties放置src目錄下，Company.hbm.xml放置在該映射的java同位置
			/*Configuration cfg = new Configuration().addClass(kiuno.hibernate.Company.class);
			sessionFactory = cfg.buildSessionFactory();*/
			/*****************************************************************************/
			
			/*****************************************************************************/
			//利用Annotations來配置，可以使用標準的JPA以取得與JPA/EJB 3.0的最大相容性
			//slf4j.jar已經太舊，改用slf4j.api-1.6.1.jar
			//sessionFactory = new AnnotationConfiguration().configure("hibernateAnno.cfg.xml").buildSessionFactory();
			//PS:使用之前要將Company都改為CompanyAnnotation
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
