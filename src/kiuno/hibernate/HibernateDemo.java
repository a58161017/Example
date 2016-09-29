package kiuno.hibernate;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import kiuno.example.logger.*;

public class HibernateDemo {
	private static MyLogger mylogger = null;
	private static Logger log = null;
	public static void main(String[] args) {
		mylogger = new MyLogger(HibernateDemo.class);
		log = mylogger.getLogger();
		
		// �}��Session�A�۷��}��JDBC��Connection
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		//insertData(session);
		//updateData(session);
		//selectDataByCriteria(session);
		selectDataByHQL(session);
		
		session.close();

		System.out.println("�s�W���OK!�Х���SQLite Browser�[�ݵ��G�I");
		//log.debug("�s�W���OK!�Х���SQLite Browser�[�ݵ��G�I");

		HibernateUtil.shutdown();
	}
	
	public static void insertData(Session session){
		Company company = new Company();
		
		//company.setId(new Long(6));
		company.setName("karon");
		company.setAge(new Long(26));
		company.setAddress("Japen");
		company.setSalary(32000.0);
		
		// Transaction��ܤ@�շ|�ܾާ@
		Transaction tx = session.beginTransaction();
		
		// �N����M�g�ܸ�Ʈw��椤�x�s
		session.save(company);
		tx.commit();
	}
	
	public static void updateData(Session session){
		Company company = new Company();
		
		company.setId(new Long(11));
		company.setName("Achar");
		company.setAge(new Long(30));
		company.setAddress("Taipai");
		company.setSalary(40000.0);
		
		// Transaction��ܤ@�շ|�ܾާ@
		Transaction tx = session.beginTransaction();
		
		// �N����M�g�ܸ�Ʈw��椤�x�s
		session.update(company);
		tx.commit();
	}
	
	public static void selectDataByHQL(Session session){
		
		Query query = session.createQuery("select com.id,com.name,com.age,com.address,com.salary,dpt.dpt_name from Company com, Department dpt where com.id=dpt.id"); 
		List companies = query.list();
		Iterator iterator =  companies.iterator();
		while(iterator.hasNext()) {
			Object[] obj = (Object[])iterator.next();
		    System.out.println(obj[0]+"/"+obj[1]+"/"+obj[2]+"/"+obj[3]+"/"+obj[4]+"/"+obj[5]);
		}
		
		//Query query = session.createQuery("from Company where name = 'Mark'"); 
		/*Query query = session.getNamedQuery("kiuno.hibernate.QueryCompany");
		query.setDouble("sal", 30000.0);
		
		List companies = query.list();
		Iterator iterator =  companies.iterator();
		while(iterator.hasNext()) {
			Company comm = (Company)iterator.next();
		    System.out.println(comm.getName()+"/"+comm.getAge());
		}*/
		
	}
	
	public static void selectDataByCriteria(Session session){
		Criteria criteria = session.createCriteria(Company.class);
        // �d��company�Ҧ����
        Iterator companies = criteria.list().iterator();
        System.out.println("id \t name/age");
        while(companies.hasNext()) {
            Company company = (Company) companies.next(); 
            System.out.println(company.getId() +
                                " \t " + company.getName() +
                                "/" + company.getAge() +
                                "/" + company.getAddress() +
                                "/" + company.getSalary()); 
        }

        // �d��company���ŦX������� by where
        /*criteria.add(Restrictions.gt("salary", 30000.0));
        criteria.add(Restrictions.lt("salary", 38000.0));*/
        /*criteria.add(Restrictions.or(
        		Restrictions.eq("age", new Long(20)),
        		Restrictions.isNotNull("age")
        ));*/
        criteria.add(Restrictions.like("name", "%ar%"));
        //criteria.add(Expression.like("name", "ar%")); //Restrictions are more better.
        
        // �Ƨ�company���ŦX������� by order
        //criteria.addOrder(Order.asc("salary"));
        
        // �s��company���ŦX������� by group
        /*ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.groupProperty("name"));
        projectionList.add(Projections.rowCount());
        criteria.setProjection(projectionList);*/
        
        companies = criteria.list().iterator(); 
        System.out.println("id \t name/age");
        while(companies.hasNext()) {
        	Company company = (Company) companies.next(); 
            System.out.println(company.getId() +
                " \t " + company.getName() +
                "/" + company.getAge() +
                "/" + company.getAddress() +
                "/" + company.getSalary()); 
        }
	}
}