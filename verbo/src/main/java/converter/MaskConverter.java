package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("maskConverter")
public class MaskConverter implements Converter<Object> {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uIcomponent, String mask) {
		if (mask != null) {
			if (mask.trim().equals("")) {
				return null;
			} else {
				mask = mask.replace("-", "");
				mask = mask.replace(".", "");
				mask = mask.replace("/", "");
				mask = mask.replace(" ", "");
				mask = mask.replace("(", "");
				mask = mask.replace(")", "");
				return mask;
			}
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uIcomponent, Object object) {
		if (object != null)
			return object.toString();

		return null;
	}

}
