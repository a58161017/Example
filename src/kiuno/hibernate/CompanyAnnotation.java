package kiuno.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "Company")
public class CompanyAnnotation {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id") // 非必要，在欄位名稱與屬性名稱不同時使用
	private Long id;
	
	@Column(name = "name") // 非必要，在欄位名稱與屬性名稱不同時使用
	private String name;
	
	@Column(name = "age") // 非必要，在欄位名稱與屬性名稱不同時使用
	private Long age;
	
	@Column(name = "address") // 非必要，在欄位名稱與屬性名稱不同時使用
	private String address;
	
	@Column(name = "salary") // 非必要，在欄位名稱與屬性名稱不同時使用
	private Double salary;

	// 必須要有一個預設的建構方法
	// 以使得Hibernate可以使用Constructor.newInstance()建立物件
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
