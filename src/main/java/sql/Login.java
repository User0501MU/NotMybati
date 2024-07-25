/*doPostメソッド内にログイン処理のプログラムを全て書いても良いのですが、冗長的なプログラムになる為、
 * ログイン画面で入力された値がデータベースに存在するか（存在すればログイン成功とする）の確認を行うSQLの処理を別クラスで作成します。*/

//ログイン画面で入力された値（管理者IDとパスワード）とデータベースに登録された値が一致するかの確認を行うSQLの実装
package sql;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;//2追加
import java.util.List;//2追加

import config.DBconfig;
import object.Admin;
import object.Customer;//2追加

public class Login {
	//以下のメソッド定義は、特定の管理者IDとパスワードをチェックして、適切な Admin オブジェクトを返すメソッド
	public Admin check(String admin_id, String password) throws FileNotFoundException {

		// データベースへの接続情報をプロパティファイルから取得
		DBconfig db_info = new DBconfig();
//		String url = db_info.getDBinfo().get("jdbc:mysql://localhost:3306/customer_management_db");
		String url = db_info.getDBinfo().get("url");
		String user = db_info.getDBinfo().get("user");
		String pass = db_info.getDBinfo().get("password");
//		String url = "jdbc:mysql://localhost/customer_management_db";　
//		String user = "root";
//		String pass = "20240501Umonica3!!";

		// 実行SQLクエリ：データベースさんに対する「おい！これやれよ」な命令文
		String login_sql = "select * from admin_tb "
				+ "where admin_id = ? and password = ?;";
		// 管理者のオブジェクトを作成
		Admin admin = new Admin();

		// データベースへの接続
		// try〜catch〜resources構文を使用して、接続が自動的に閉じられるようにしているんや
		try(Connection conn = DriverManager.getConnection(url,user,pass)) {
			PreparedStatement stmt = conn.prepareStatement(login_sql);
			//▶PreparedStatement: パラメータ化(処理結果に影響を与える外部から投入される変動要素)されたSQLクエリ（login_sql）を実行するためのオブジェクト
			//▶conn.prepareStatement(login_sql): 先ほどの Connection オブジェクトを使用して指定されたDBに接続可能となる、

			/*・　PreparedStatement
　　            ① SQL文を受け取って解析し、値があればいつでも実行できる状態にします。
　　            ② SQL文に必要な値をセットします。
　　            ③ SQL文を実行します。*/

			// プレースホルダに値を設定：正式な値が入るまで(?)で一時的に場所を確保しておくために入れておく値
			// 変数login_sqlの一番目の?に引数のuser_idをセット
			stmt.setString(1, admin_id);
			// 変数login_sqlの二番目の?に引数のpasswordをセット
			stmt.setString(2, password);

			 //▶stmt は PreparedStatement オブジェクトの変数名である  。
			/*?executeQuery() メソッドを使って、準備された login_sql を実行し、(login_sql という変数に、SQLクエリとして "SELECT * FROM admin_tb WHERE admin_id = ? AND password = ?;" という文字列を格納しているんや。）
			 *指定された admin_id と password に基づくデータをデータベースから取得するんや。
			 *結果格納する ResultSet オブジェクト rs に格納され、これを使ってデータを取り出すことができるんや。
			 */
			ResultSet rs = stmt.executeQuery();

			//結果の処理
			// データベースから取得した値をAdminオブジェクトに格納
			// 値がなければ、login_flag（false）のみ格納
			if(rs.next()) {
				admin.setId(rs.getInt("admin_id"));
				admin.setName(rs.getString("name"));
				admin.setPassword(rs.getString("password"));
				admin.setLogin_flag(true);
			} else {
				admin.setLogin_flag(false);
			}
		} //▶catch ブロックは、SQL例外（SQLException）が発生したときに実行されるコード
			catch (SQLException e) {
			System.out.println("データベースとの接続を閉じます");
		//▶エラーが発生したときに表示される内容で、そのエラーが発生するまでの過程（どんな処理がどの順番で呼び出されたかの流れ）を、ざっくりと表示したもの
			e.printStackTrace();
		}
		// データベースから取得した値を返す
		return admin;
	}

	//★追記（2顧客情報取得）
	//ログイン成功後に管理者が管理する顧客情報の取得
	public List<Customer> getCustomerInfo(String admin_id) throws FileNotFoundException {

		// データベースへの接続情報をプロパティファイルから取得
		DBconfig db_info = new DBconfig();
		String url = db_info.getDBinfo().get("url");//dar読み込みエラー
		String user = db_info.getDBinfo().get("user");
		String pass = db_info.getDBinfo().get("password");

		// 実行SQL
		// admin_id(管理者ID)で該当する顧客情報を取得する
		String customer_sql = "select * from customer_tb "
				            + "where admin_id = ?;";

		// 顧客情報のデータを格納するListを作成
		List<Customer> cus_list = new ArrayList<Customer>();

		try(Connection conn = DriverManager.getConnection(url, user, pass)){
			PreparedStatement stmt = conn.prepareStatement(customer_sql);

			stmt.setString(1, admin_id);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				// 顧客情報用のオブジェクトを作成
				Customer cus_info = new Customer();
				// オブジェクトにデータを一時格納
				cus_info.setCustomer_id(rs.getInt("customer_id"));
				cus_info.setAdmin_id(rs.getInt("admin_id"));
				cus_info.setName(rs.getString("name"));
				cus_info.setAddress(rs.getString("address"));
				cus_info.setRegistered_time(rs.getDate("registered_time"));
				cus_info.setUpdated_time(rs.getDate("updated_time"));

				// オブジェクトに格納された
				// 顧客情報用のデータをリストに加える
				cus_list.add(cus_info);
			}
		} catch (SQLException e) {
			System.out.println("データベースとの接続を閉じます");
			e.printStackTrace();
		}
		return cus_list;
	}
}