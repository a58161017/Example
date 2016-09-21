package kiuno.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "Company")
public class CompanyAnnotation {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id") // �D���n�A�b���W�ٻP�ݩʦW�٤��P�ɨϥ�
	private Long id;
	
	@Column(name = "name") // �D���n�A�b���W�ٻP�ݩʦW�٤��P�ɨϥ�
	private String name;
	
	@Column(name = "age") // �D���n�A�b���W�ٻP�ݩʦW�٤��P�ɨϥ�
	private Long age;
	
	@Column(name = "address") // �D���n�A�b���W�ٻP�ݩʦW�٤��P�ɨϥ�
	private String address;
	
	@Column(name = "salary") // �D���n�A�b���W�ٻP�ݩʦW�٤��P�ɨϥ�
	private Double salary;

	// �����n���@�ӹw�]���غc��k
	// �H�ϱoHibernate�i�H�ϥ�Constructor.newInstance()�إߪ���
	public CompanyAnnotation() {
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}
}
