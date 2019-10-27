package com.mysynergis.soe.failure;

import com.esri.arcgis.server.json.JSONObject;
import com.mysynergis.soe.config.IConstant;
import com.mysynergis.soe.config.impl.EKey;
import com.mysynergis.soe.log.ECode;

/**
 * exception specific to problems during parsing<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public class ParseFailure extends ACodedFailure {

    private static final long serialVersionUID = -1946745741529182034L;

    public ParseFailure(String message, Throwable cause) {
        super(message, cause, ECode.ERROR_PARSE);
    }

    public <T extends IConstant> ParseFailure(Class<T> type, String invalidValue, T[] allowedValues) {
        super(getInvalidConstantMessage(type, invalidValue, allowedValues), null, ECode.ERROR_PARSE);
    }

    public ParseFailure(String type, String invalidValue, String[] allowedValues) {
        super(getInvalidTypeMessage(type, invalidValue, allowedValues), null, ECode.ERROR_PARSE);
    }

    public ParseFailure(String type, JSONObject examinee, EKey... keys) {
        super(getMissingNodesMessage(type, examinee, keys), null, ECode.ERROR_PARSE);
    }

    public static <T extends IConstant> String getInvalidConstantMessage(Class<T> type, String invalidValue, T[] allowedValues) {
        String[] allowedValueStrings = new String[allowedValues.length];
        for (int i = 0; i < allowedValues.length; i++) {
            allowedValueStrings[i] = allowedValues[i].getConstant();
        }
        return getInvalidTypeMessage(type.getSimpleName(), invalidValue, allowedValueStrings);
    }

    protected static String getInvalidTypeMessage(String type, String invalidValue, String[] allowedValues) {
        StringBuilder invalidTypeMessageBuilder = new StringBuilder();
        invalidTypeMessageBuilder.append("failed to resolve a ");
        invalidTypeMessageBuilder.append(type);
        invalidTypeMessageBuilder.append(" value (invalid: ");
        invalidTypeMessageBuilder.append(invalidValue);
        invalidTypeMessageBuilder.append(", allowed: [");
        String separator = "";
        for (String allowedValue : allowedValues) {
            invalidTypeMessageBuilder.append(separator);
            invalidTypeMessageBuilder.append(allowedValue);
            separator = ", ";
        }
        invalidTypeMessageBuilder.append("])");
        return invalidTypeMessageBuilder.toString();
    }

    protected static String getMissingNodesMessage(String type, JSONObject examinee, EKey... keys) {
        StringBuilder missingNodesMessageBuilder = new StringBuilder();
        missingNodesMessageBuilder.append("all of [");
        String separator = "";
        for (EKey key : keys) {
            missingNodesMessageBuilder.append(separator);
            missingNodesMessageBuilder.append(key.getConstant());
            missingNodesMessageBuilder.append(":");
            if (examinee.has(key.getConstant())) {
                missingNodesMessageBuilder.append(" OK");
            } else {
                missingNodesMessageBuilder.append(" NA");
            }
            separator = ", ";
        }
        missingNodesMessageBuilder.append("] must be present in a ");
        missingNodesMessageBuilder.append(type);
        missingNodesMessageBuilder.append(" configuration");
        return missingNodesMessageBuilder.toString();
    }

}
