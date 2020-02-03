{{#layout}}

<div class="container">
	<h1>Регистрация</h1>

	{{>inc/error_msg}}

	<form method=post>
		<div class="form-group">
			<label for="email">E-mail</label>
			<input id="email"
				   type=text
				   class="form-control"
				   name=email
				   placeholder="example@mail.com"
				   value="{{userRegistrationForm.email}}">
		</div>

		<div class="form-group">
			<label for=password>Пароль (не от мыла)</label>
			<input id=password
				   type=password
				   class="form-control"
				   name=password
				   placeholder="Пар0ль1"
				   value='{{userRegistrationForm.password}}'>
			<small class="text-danger">{{formError.password}}</small>
		</div>
		<div class="form-group">
			<label for=passwordConfirm>Пароль еще разок</label>
			<input id=passwordConfirm
				   type=password
				   class="form-control"
				   name=passwordConfirm
				   placeholder="Пар0ль1"
				   value='{{userRegistrationForm.passwordConfirm}}'>
			<small class="text-danger">{{formError.passwordConfirm}}</small>
		</div>
		<div class="form-group">
			<label for=userName>Как к Вам обращаться</label>
			<input id=userName
				   type=text
				   class="form-control"
				   name=name
				   placeholder="Иванов Иван"
				   value="{{userRegistrationForm.name}}">
			<small class="text-danger">{{formError.name}}</small>
		</div>
		<input type=submit class="btn btn-lg btn-primary" name=register_submit value='Готово'>
		<a class="btn btn-lg btn-outline-secondary" href="{{layout.contextPath}}/login">Войти</a>
	</form>
</div>

{{/layout}}
