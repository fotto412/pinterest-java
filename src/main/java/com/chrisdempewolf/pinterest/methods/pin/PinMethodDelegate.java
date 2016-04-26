package com.chrisdempewolf.pinterest.methods.pin;

import com.chrisdempewolf.pinterest.exceptions.PinterestException;
import com.chrisdempewolf.pinterest.fields.pin.PinFields;
import com.chrisdempewolf.pinterest.methods.network.NetworkHelper;
import com.chrisdempewolf.pinterest.methods.network.ResponseMessageAndStatusCode;
import com.chrisdempewolf.pinterest.responses.pin.PinPage;
import com.chrisdempewolf.pinterest.responses.pin.PinResponse;
import com.chrisdempewolf.pinterest.responses.pin.Pins;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.chrisdempewolf.pinterest.methods.pin.PinEndPointURIBuilder.buildBoardPinUri;
import static com.chrisdempewolf.pinterest.methods.pin.PinEndPointURIBuilder.buildMyPinUri;
import static com.chrisdempewolf.pinterest.methods.pin.PinEndPointURIBuilder.buildPinUri;

public class PinMethodDelegate {
    private final String accessToken;

    public PinMethodDelegate(final String accessToken) {
        this.accessToken = accessToken;
    }

    public PinResponse getPin(final String id) {
        try {
            return new Gson().fromJson(IOUtils.toString(buildPinUri(accessToken, id, null)), PinResponse.class);
        } catch (URISyntaxException | IOException e) {
            throw new PinterestException(e.getMessage(), e);
        }
    }

    public PinResponse getPin(final String id, final PinFields pinFields) {
        try {
            return new Gson().fromJson(IOUtils.toString(buildPinUri(accessToken, id, pinFields.build())), PinResponse.class);
        } catch (URISyntaxException | IOException e) {
            throw new PinterestException(e.getMessage(), e);
        }
    }

    /**
     * I adopted the true/false pattern for deletion from RestFB
     * @param id:  Pin ID
     * @return true iff deletion was successful; false otherwise
     */
    public boolean deletePin(final String id) {
        try {
            final ResponseMessageAndStatusCode response = NetworkHelper.submitDeleteRequest(new HttpDelete(buildPinUri(accessToken, id, null)));

            return response.getStatusCode() == HttpStatus.SC_OK;
        } catch (URISyntaxException | IOException e) {
            throw new PinterestException(e.getMessage(), e);
        }
    }

    public Pins getMyPins() {
        try {
            return new Gson().fromJson(IOUtils.toString(buildMyPinUri(accessToken, null)), Pins.class);
        } catch (URISyntaxException | IOException e) {
            throw new PinterestException(e.getMessage(), e);
        }
    }

    public Pins getMyPins(final PinFields pinFields) {
        try {
            return new Gson().fromJson(IOUtils.toString(buildMyPinUri(accessToken, pinFields.build())), Pins.class);
        } catch (URISyntaxException | IOException e) {
            throw new PinterestException(e.getMessage(), e);
        }
    }

    public Pins getPinsFromBoard(final String boardName) {
        try {
            return new Gson().fromJson(IOUtils.toString(buildBoardPinUri(accessToken, boardName, null)), Pins.class);
        } catch (URISyntaxException | IOException e) {
            throw new PinterestException(e.getMessage(), e);
        }
    }

    public Pins getPinsFromBoard(final String boardName, final PinFields pinFields) {
        try {
            return new Gson().fromJson(IOUtils.toString(buildBoardPinUri(accessToken, boardName, pinFields.build())), Pins.class);
        } catch (URISyntaxException | IOException e) {
            throw new PinterestException(e.getMessage(), e);
        }
    }

    public Pins getNextPageOfPins(final PinPage page) {
        if (page == null || page.getNext() == null) { return null; }

        try {
            return new Gson().fromJson(IOUtils.toString(new URI(page.getNext())), Pins.class);
        } catch (URISyntaxException | IOException e) {
            throw new PinterestException(e.getMessage(), e);
        }
    }
}
