
{{#paginationBar.isMoreThanOnePage}}
	<nav aria-label="Page navigation">
		<ul class="pagination">
			{{#paginationBar.hasPreviousPage}}
				<li class="page-item"><a class="page-link" href='{{layout.contextPath}}{{paginationBar.baseUrl}}&page={{paginationBar.previousPage}}'>< сюда</a></li>
			{{/paginationBar.hasPreviousPage}}

			<li class="page-item active"><a class="page-link">{{paginationBar.currentPage}}</a></li>

			{{#paginationBar.hasNextPage}}
				<li class="page-item"><a class="page-link" href='{{layout.contextPath}}{{paginationBar.baseUrl}}&page={{paginationBar.nextPage}}'>туда ></a></li>
			{{/paginationBar.hasNextPage}}
		</ul>
	</nav>
{{/paginationBar.isMoreThanOnePage}}
