package servlet;

import java.io.IOException;
import java.util.List;//2追記

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.Admin;
import object.Customer;//追記
import sql.Login;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// ログイン画面を表示させる
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
		}
		/*
		 * 1,リクエストを取得: まず、request.getRequestDispatcher("WEB-INF/jsp/login.jsp")
		 * っていうのは、ログインページ（login.jsp）を画面遷移URL表示するための準備をしてるんだよ。 2,ページを表示する:
		 * 次に、dispatcher.forward(request, response)
		 * っていうのは、その準備ができたら、実際に「ログインページ」にリクエストを送って、そのページを見せるようにしてるの。
		 * 要するに、このコードは「今のページからログインページに移動するけど、URLはそのままにしておく」っていうことをしてるんだ。だから、
		 * ユーザーが見るURLは変わらないけど、実際にはログインページが表示されるってわけだね。
		 */

		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// 作成したメソッドを呼び出し元に追記： ★ログイン処理の実装
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードの設定
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		// ログイン画面で入力された値を取得 ★login.jspの「name=”admin_id” と name=”password”」から取得
		String user_id = request.getParameter("admin_id");
		String password = request.getParameter("password");

		// TODO Auto-generated method stub このメソッドは、まだ実際の処理が実装されていないことを示してる
		// doGet(request, response);

		// ★Login.javaで作成したcheckメソッドをLoginServletのdoPostメソッドで使用できるようにし、ログイン機能を完成させます。
		// ログイン画面で入力された値をもとにデータベースに登録された管理者の値を取得
		// 入力された情報でデータベースから値が取得できない場合ログイン失敗
		Login login = new Login();
		Admin admin = login.check(user_id, password);//check

		// admin.isLogin_flag() メソッドを呼び出して、ログインフラグが true かどうかを確認
		if (admin.isLogin_flag()) {
			// ログイン成功 → 次の画面へ遷移
			System.out.println("ログイン成功");

//--2追加ココから、
			List<Customer> customer = null;
			String admin_id = null;
			// データベースから取得した顧客情報を格納
			customer = login.getCustomerInfo(admin_id);

			// 格納した顧客情報を遷移先の画面に渡す
			//▶request.setAttribute(“データの名前”,登録するデータ)
			request.setAttribute("customer", customer);

//--2追加ココまで。


			// ▶「ページ転送先指定」request.getRequestDispatcher
			// という方法で、移動先のページ（WEB-INF/jsp/customer_list.jsp）を指定しているんや
			// ▶「ページに移動する」dispatcher?.forwardフォワード(request, response)
			// を使って、今のページから指定したページ（customer_list.jsp）に移動するんや。
			// ※画面の出力をJSPで行う場合、サーブレットからforwardを利用して処理の転送を行う。

			RequestDispatcher dispatcher =
					request.getRequestDispatcher("WEB-INF/jsp/customer_list.jsp");
			dispatcher.forward(request, response);

			/*
			 * 【補足】 dispatcher（RequestDispatcher） は、リクエストとレスポンスを指定したページに転送するためのツールや。
			 * 使い方：getReqestDispatcher で転送先を決める。 forward でリクエストとレスポンスを転送して、新しいページを表示する。
			 */

		} else {
			 // ログイン失敗 → ログイン画面へ遷移 .
			System.out.println("ログイン失敗");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);

			// admin.isLogin_flag()がtrueであればログイン後の画面（顧客一覧画面；customer_list.jsp）へ、falseであればログイン画面を再度login.jsp表示させます。

		}
	}
}