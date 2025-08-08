package com.biblioteca.gutendex;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GutendexResponse {

	private String next;
	private String previous;
	private List<Result> results;

	public GutendexResponse() {
	}

	@JsonProperty("next")
	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	@JsonProperty("previous")
	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	@JsonProperty("results")
	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Result {
		private Long id;
		private String title;
		private List<Person> authors;
		private List<String> subjects;
		private Integer downloadCount;

		public Result() {
		}

		@JsonProperty("id")
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		@JsonProperty("title")
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		@JsonProperty("authors")
		public List<Person> getAuthors() {
			return authors;
		}

		public void setAuthors(List<Person> authors) {
			this.authors = authors;
		}

		@JsonProperty("subjects")
		public List<String> getSubjects() {
			return subjects;
		}

		public void setSubjects(List<String> subjects) {
			this.subjects = subjects;
		}

		@JsonProperty("download_count")
		public Integer getDownloadCount() {
			return downloadCount;
		}

		public void setDownloadCount(Integer downloadCount) {
			this.downloadCount = downloadCount;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Person {
		private String name;
		private Integer birthYear;
		private Integer deathYear;

		public Person() {
		}

		@JsonProperty("name")
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@JsonProperty("birth_year")
		public Integer getBirthYear() {
			return birthYear;
		}

		public void setBirthYear(Integer birthYear) {
			this.birthYear = birthYear;
		}

		@JsonProperty("death_year")
		public Integer getDeathYear() {
			return deathYear;
		}

		public void setDeathYear(Integer deathYear) {
			this.deathYear = deathYear;
		}
	}
}
