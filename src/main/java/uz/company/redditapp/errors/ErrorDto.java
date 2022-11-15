package uz.company.redditapp.errors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDto implements Serializable {

    String timeStamp;

    int status;

    String error;

    String message;

    String path;

    String title;

    String detail;
}
