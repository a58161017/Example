package kiuno.hibernate;

public class Department {
	private Long id;
	private String dpt_name;

	// 必須要有一個預設的建構方法
	// 以使得Hibernate可以使用Constructor.newInstance()建立物件
	public Department() {
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDpt_name() {
		return dpt_name;
	}

	public void setDpt_name(String dpt_name) {
		this.dpt_name = dpt_name;
	}
}
