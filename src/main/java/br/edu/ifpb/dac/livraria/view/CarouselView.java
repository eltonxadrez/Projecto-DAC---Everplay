package br.edu.ifpb.dac.livraria.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class CarouselView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> slides;

	@PostConstruct
	public void init() {
		this.slides = new ArrayList<String>();
		for (int i = 1; i < 4; i++) {
			slides.add("slide" + i);
		}
		;
	}

	public List<String> getSlides() {
		return slides;
	}

	public void setSlides(List<String> slides) {
		this.slides = slides;
	}

}
