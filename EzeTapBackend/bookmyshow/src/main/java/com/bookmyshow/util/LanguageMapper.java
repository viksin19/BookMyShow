package com.bookmyshow.util;

import java.util.function.Function;

import com.bookmyshow.entity.Language;

public class LanguageMapper {

	public static Function<String, Language> languageNameToLanguageEntity() {
		return (String languageName)->{
			Language language = new Language();
			language.setLanguage(languageName);
			return language;
		};
	}
}
