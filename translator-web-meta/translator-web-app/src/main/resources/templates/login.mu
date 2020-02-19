{{#layout}}

<div class="container">
	<h1>Вход</h1>

	{{>inc/error_msg}}

	<!-- Nav tabs -->
	<ul class="nav nav-tabs">
		<li class="nav-item">
			<a class="nav-link active" data-toggle="tab" href="#social">Соц.сети</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" data-toggle="tab" href="#email">По-старинке</a>
		</li>
	</ul>

	<!-- Tab panes -->
	<div class="tab-content">
		<div id="social" class="container tab-pane active"><br>
			<a class="btn btn-dark" href="{{layout.contextPath}}/oauth2?provider=GitHub">GitHub</a>
			<a class="disabled btn btn-primary" href="{{layout.contextPath}}/oauth2?provider=VK">ВКонтакте (скоро)</a>
		</div>
		<div id="email" class="container tab-pane fade"><br>
			<form method=post>
				<div class="form-group">
					<label for="userName">Ваше имя</label>
					<input id="userName"
						   type=text
						   class="form-control"
						   name=username
						   placeholder="Иванов Иван">
				</div>
				<div class="form-group">
					<label for="password">Пароль</label>
					<input id="password"
						   type=password
						   class="form-control"
						   name=password
						   placeholder="Пароль">
				</div>
				<input type=submit class="btn btn-lg btn-success" name=login_submit value='Войти'>
				<a class="btn btn-lg btn-outline-primary" href='registration'>Регистрация</a>
			</form>
		</div>
	</div>
</div>
{{/layout}}