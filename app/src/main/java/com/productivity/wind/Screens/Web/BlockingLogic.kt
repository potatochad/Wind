package com.productivity.wind.Screens.Web

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.NavControl.*
import com.productivity.wind.Imports.Utils.String.*
import com.productivity.wind.*
import com.productivity.wind.Imports.Utils.Browser.*


fun BlockingLogic(web: WebController){
	fun Block(){
		goTo("WebHome")
		Bar.Url = "google.com"
	}
	fun youtubeFilter(){
		web.web?.youtubeFilter(
			allowOnly = listOf(
						"Stefan Frädrich",
		"Greator",
		"Christian Bischoff",
		"Tobias Beck",
		"Dirk Kreuter",
		"Maxim Mankevich",
		"Mark Maslow",
		"Tim Grover",
		"Stefan James",
		"Veit Lindau",
		"Laura Malina Seiler",
		"Patrick Reiser",
		"Florian Homm",
		"Thorsten Havener",
		"Daniel Jung",
		"Felix Beilharz",
		"Joachim Simon",
		"Maximilian Schwarzmüller",
		"Julien Backhaus",
		"Philipp Steuer",
			"Disziplin",
			"harte Arbeit",
			"hart arbeiten",
			"Durchhaltevermögen",
			"Ausdauer",
			"Fokus",
			"Entschlossenheit",
			"Selbstdisziplin",
			"mentale Stärke",
			"Willenskraft",
			"Beharrlichkeit",
			"Durchhaltevermögen",
			"Belastbarkeit",
			"Verantwortung",
			"Selbstkontrolle",
			"Selbstverbesserung",
			"persönliche Entwicklung",
			"Zielstrebigkeit",
			"Ziele",
			"Zielsetzung",
			"Erfolg",
			"Leistung",
			"Ehrgeiz",
			"Produktivität",
			"Arbeitsmoral",
			"Arbeitsdisziplin",
			"konzentriertes Arbeiten",
			"Tiefenarbeit",
			"Gewohnheiten",
			"gute Gewohnheiten",
			"Erfolgsmindset",
			"Lebenseinstellung",
			"Motivation",
			"Motivationsrede",
			"Disziplin Motivation",
			"harte Arbeit Motivation",
			"Erfolg Motivation",
			"Selbstvertrauen",
			"Selbstständigkeit",
			"Aufgeben",
			"niemals aufgeben",
			"weitermachen",
			"Zähne zusammenbeißen",
			"über sich hinauswachsen",
			"Grenzen überwinden",
			"Komfortzone verlassen",
			"Verzicht",
			"Belohnungsaufschub",
			"Fleiß",
			"Durchziehen",
				
				"discipline",
			"hard work",
			"work hard",
			"consistency",
			"grind",
			"focus",
			"determination",
			"self discipline",
			"mental toughness",
			"resilience",
			"perseverance",
			"delayed gratification",
			"productivity",
			"deep work",
			"getting things done",
			"work ethic",
			"success",
			"achievement",
			"ambition",
			"goals",
			"goal setting",
			"self improvement",
			"personal growth",
			"motivation",
			"motivational",
			"motivation speech",
			"discipline motivation",
			"hard work motivation",
			"David Goggins",
			"Goggins",
			"David Goggins motivation",
			"stay hard",
			"Goggins mindset",
			"Jocko Willink",
			"Jocko motivation",
			"Jocko mindset",
			"Jordan Peterson",
			"Andrew Huberman",
			"study motivation",
			"study with me",
			"academic discipline",
			"exam motivation",
			"student productivity",
			"coding motivation",
			"entrepreneur motivation",
			"business motivation",
			"fitness motivation",
			"mental strength",
			"no excuses",
			"keep going",
				
				/*
				// "my first million",
				//"mindset",
				//"humility",
				//"get rid of ego",
				//"AllThingsSecured",
				//"university",
				"hard work",
				"motivation",
				//"Mel Robbins",
				//"Tony Robbins",
				"David Goggins",
				//"Darren Hardy",
				//"personal growth",
				"self improvement",
				"success mindset",
				//"entrepreneurship",
				//"business growth",
				//"financial freedom",
				//"founder mindset",
				//"startup",
				//"startup growth",
				//"building a company",
				//"innovation",
				"execution",
				//"time management",
				//"goal setting",
				//"daily habits",
				//"morning routine",
				"consistency",
				"focus",
				"determination",
				"resilience",
				"confidence",
				//"self belief",
				//"mental toughness",
				"winning mindset",
				"growth mindset",
				"high performance",
				"peak performance",
				"success habits",
				"wealth mindset",
				"millionaire mindset",
				"billionaire mindset",
				"Robert Kiyosaki",
				"Jim Rohn",
				"Brian Tracy",
				"Simon Sinek",
				"Gary Vaynerchuk",
				"Alex Hormozi",
				"Naval Ravikant",
				"Steve Jobs",
				"business lessons",
				"life lessons",
				"career growth",
				"professional development",
				"leadership skills",
				"communication skills",
				"customer obsession",
				"company culture",
				"corporate strategy",
				"investment strategy",
				"long term thinking",
				"compound growth",
				"financial literacy",
				"side hustle",
				"online business",
				"digital business",
				"content creation",
				"public speaking",
				"confidence building",
				"overcoming fear",
				"failure lessons",
				"success stories",
				"founderspodcast1",
				"work ethic",
				"inspiration",
				"motivational speech",
				"motivational podcast",
				"self development podcast"
				*/
			),
			block = listOf("MrBeast", "McYum", "Mark Rober", " TABS ", " Aliens ", "Technoblade", "Skeppy", "Grian", "Spifey", "Minecraft", "Speedrun", "Mr Bean", "POV", "m views", "redstone", "command blocks", "00 IQ", "poly bridge", "iswho", "rageplaysgames", "leowook", "shalz", "chess", " vs ", " vs. ", "reddoons", "real civil engineer", "cappy army", "iwantcheckmate", "Can I", "but everyone", " noob ", " OP ", " 1v1 ", " 3v1 ", " 2v1 ", ".io", "! ", " trackmania ", " 100% ", "Players", "Market crash", "king", "flood", "but all", "geometry dash", "just lost", "world box", "law by mike", "smartest", "baronVonGames", "...", " SMP ", "1000 days", "100 days")         
		)
	}
	web.onPageFinished{
		youtubeFilter()
	}

	
	
	web.doUpdateVisitedHistory { url, isReload ->
		Bar.Url = url ?: "https://www.google.com"

		

		Bar.Url.blog("BarUrl")
		web.blockImages({Bar.Url.hasAny("youtube.com")})

		if (Bar.Url.hasAny(
			"melrobbins.com/podcast/", 
			"mfmpod.com", 
			"youtube.com",
		)) {
			if (Bar.Url.hasAny("/shorts/")) Block()
			//Allow
		} else {
			Block()
		}
	}
	
	
	
	


	/*

	web.doUpdateVisitedHistory { url, isReload ->
		Bar.Url = url ?: "https://www.google.com"

		if (WebUtils.HasBadWord(Bar.Url, { it.locked })) Block()
			
		if (WebUtils.HasBadWord(Bar.Url)){
			if (!WebUtils.HasGoodWord(Bar.Url)){
				Block()
			}
		}
	}
	
	web.shouldInterceptRequest {
		if (it.image) return@shouldInterceptRequest null
		
		var x = WebUtils.IsGood(it)
		if (x == WebAction.Allow) return@shouldInterceptRequest null
		if (x == WebAction.Block) Block()
		if (x == WebAction.Blot) return@shouldInterceptRequest EmptyWebResource()
		
		//: WebAction



		
		return@shouldInterceptRequest null
	}
	*/
}













