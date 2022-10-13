/**
 * @author : Udyogi Siphara
 * Project Name: SuperMarketCopy
 * Date        : 5/29/2022
 * Time        : 10:20 PM
 */

package lk.para.superMarket.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static Object validateJFXTextField(LinkedHashMap<JFXTextField, Pattern> map, JFXButton btn) {
        btn.setDisable(true);
        for (JFXTextField textFieldKey : map.keySet()) {
            System.out.println("textFieldKey");
            System.out.println(textFieldKey);
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.setStyle("-fx-text-fill: red");
                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-text-fill: blue");
        }
        btn.setDisable(false);
        return true;
    }

    public static Object validateTextField(LinkedHashMap<TextField, Pattern> map, JFXButton btn) {
        btn.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            System.out.println("textFieldKey");
            System.out.println(textFieldKey);
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.setStyle("-fx-text-fill: red");
                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-text-fill: blue");
        }
        btn.setDisable(false);
        return true;
    }

    public static Object validateJFXTextFields(LinkedHashMap<JFXTextField, Pattern> map, JFXButton btn) {
        btn.setDisable(true);
        for (JFXTextField textFieldKey : map.keySet()) {
            System.out.println("textFieldKey");
            System.out.println(textFieldKey);
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.setStyle("-fx-text-fill: red");
                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-text-fill: green");
        }
        btn.setDisable(false);
        return true;
    }


    public static Object validateJFXPasswordField(LinkedHashMap<JFXPasswordField, Pattern> map, JFXButton btn) {
        btn.setDisable(true);
        for (JFXPasswordField passwordFieldKey : map.keySet()) {
            Pattern patternValue = map.get(passwordFieldKey);
            if (!patternValue.matcher(passwordFieldKey.getText()).matches()) {
                if (!passwordFieldKey.getText().isEmpty()) {
                    passwordFieldKey.setStyle("-fx-text-fill: red");
                }
                return passwordFieldKey;
            }
            passwordFieldKey.setStyle("-fx-text-fill: green");
        }
        btn.setDisable(false);
        return true;
    }


}
