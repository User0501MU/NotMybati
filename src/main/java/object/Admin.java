// データーベースから取得した（ID,ユーザー名,PWの格納）
/*mysql> desc admin_tb;
+----------+-------------+------+-----+---------+----------------+
| Field    | Type        | Null | Key | Default | Extra          |
+----------+-------------+------+-----+---------+----------------+
| admin_id | int         | NO   | PRI | NULL    | auto_increment |
| name     | varchar(20) | NO   |     | NULL    |                |
| password | varchar(20) | NO   |     | NULL    |                |
+----------+-------------+------+-----+---------+----------------+
3 rows in set (0.02 sec)

mysql> insert into admin_tb(name , password) values("管理者1" , "11111111");
Query OK, 1 row affected (0.01 sec)

mysql> select * from admin_tb;
+----------+------------+----------+
| admin_id | name       | password |
+----------+------------+----------+
|        1 | 管理者1    | 11111111 |
+----------+------------+----------+*/
package object;

public class Admin {

	private int id;
	private String name;
	private String password;
	private boolean login_flag;//フラグ(true/false「オン（on）」か「オフ（off）」のどちらかの状態を表す値が入る変数)

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLogin_flag() {
		return login_flag;
	}

	public void setLogin_flag(boolean login_flag) {
		this.login_flag = login_flag;
	}
}