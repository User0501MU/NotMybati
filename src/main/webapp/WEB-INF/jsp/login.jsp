
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%--BootStrapを使用するためのCDN--%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">


<title>ログイン画面</title>
</head>
<body>
	<%--style=は、HTML要素にインラインで直接CSSスタイルを適用するための属性--%>
	<div class="mx-auto" style="width: 300px;">
		<%--max-autoはmargin左右自動調節の中央揃え、widthは幅--%>
		<h1 class="mb-3" style="text-align: center">管理者ログイン画面</h1>
		<%--text-align:テキスト整列 center"中心「見出しを中央揃え」--%>

		<%--ログインボタンを押下後、処理をLoginServletのdoPostメソッドに渡す--%>
		<form action="/CustomerManagement/LoginServlet" method="post">
			<div class="mb-3">

				<%--for属性は、ラベルがどのフォーム要素に関連しているかを指定するためのもの、ラベルをクリックすると、そのIDを持つフォーム要素（例えば、<input>）にフォーカスが移るようになる--%>
				<label for="adminID" class="form-label">管理者ID</label>
				<%--※入力された管理者IDの値をLoginServletのdoPostメソッドに渡す--%>
				<input type="text" class="form-control" id="adminID" name="admin_id">
			</div>

			<div class="mb-3">
				<label for="pass" class="form-label">パスワード</label>
				<%--※入力されたパスワードの値をLoginServletのdoPostメソッドに渡す--%>
				<input type="password" class="form-control" id="pass" name="password">
			</div>
				<%--ログインボタン--%>
				<button type="submit" class="btn btn-primary">ログイン</button>
		</form>
	</div>
	<%--http://localhost:8080/CustomerManagement/LoginServlet--%>
</body>
</html>

