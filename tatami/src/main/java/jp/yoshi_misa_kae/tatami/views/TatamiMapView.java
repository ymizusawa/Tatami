package jp.yoshi_misa_kae.tatami.views;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import jp.yoshi_misa_kae.tatami.PermissionUtils;
import jp.yoshi_misa_kae.tatami.R;

/**
 * Created by Yoshitaka Mizusawa on 2016/08/21.
 */
public class TatamiMapView extends MapView implements OnMapReadyCallback {

    public static final float INIT_ZOOM_LEVEL = 17.0f;

    private GoogleMap map;
    private LatLng latLng;
    private boolean mapLoaded = false;
    private MarkerOptions options;
    private LatLng drawCircleLatLng;
    private float drawCircleRadius;
    private int drawCircleColor;

    public TatamiMapView(Context context) {
        this(context, null);
    }

    public TatamiMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        if (!isInEditMode()) {
            MapsInitializer.initialize(context);

            onCreate(null);
            onResume();

            getMapAsync(this);
        } else {
            init(context);
        }
    }

    private void init(Context context) {
        View.inflate(context, R.layout.tatami_map_view, this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        if (PermissionUtils.hasPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (latLng != null)
                movePosition(latLng, INIT_ZOOM_LEVEL, false);

            //noinspection MissingPermission
            map.setMyLocationEnabled(false);
            map.getUiSettings().setAllGesturesEnabled(false);
            map.getUiSettings().setMapToolbarEnabled(false);
            map.getUiSettings().setMyLocationButtonEnabled(false);
            map.setOnMapLoadedCallback(onMapLoadedCallback());
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
        }
    }

    public GoogleMap.OnMapLoadedCallback onMapLoadedCallback() {
        return
                new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        mapLoaded = true;
                        if (latLng != null)
                            movePosition(latLng, INIT_ZOOM_LEVEL, false);

                        if (options != null) {
                            Marker marker = map.addMarker(options);
                            marker.showInfoWindow();

                            map.addCircle(new CircleOptions().center(drawCircleLatLng)
                                    .radius(drawCircleRadius).strokeColor(drawCircleColor));
                        }
//                        if (bean.id != -1) {
//                            mvp.movePosition(bean.getLatLng(), AreaSelectFragment.INIT_ZOOM_LEVEL, false);
//                            mvp.drawCircle(bean.getLatLng(), bean.radius);
//                        }
////                ((AreaSelectActivity) mvp.getActivityData()).showMenuSave(isMapLoaded);
//
//                        setRadius();
                    }
                };
    }

    public void movePosition(LatLng latLng, float zoomLevel, boolean isAnimation) {
        if (map != null) {
            CameraUpdate camera = createCamera(latLng, zoomLevel);
            if (isAnimation) {
                map.animateCamera(camera);
            } else {
                map.moveCamera(camera);
            }
        }
    }

    /**
     * カメラを作成する
     *
     * @param latLng
     * @param zoomLevel
     * @return
     */
    protected CameraUpdate createCamera(LatLng latLng, float zoomLevel) {
        return CameraUpdateFactory
                .newCameraPosition(new CameraPosition.Builder()
                        .target(latLng).zoom(zoomLevel).build());
    }

    public void changeLocation(@NonNull LatLng latLng) {
        this.latLng = latLng;

        if (mapLoaded) movePosition(latLng, INIT_ZOOM_LEVEL, false);
    }

    public void clearMap() {
        if (map != null) map.clear();
    }

    public void drawCircle(LatLng latLng, float radius, int color) {
        options = new MarkerOptions();
        options.position(latLng);
        options.draggable(false);

        if (map == null) {
            drawCircleLatLng = latLng;
            drawCircleRadius = radius;
            drawCircleColor = color;
        } else {
            Marker marker = map.addMarker(options);
            marker.showInfoWindow();

            map.addCircle(new CircleOptions().center(latLng)
                    .radius(radius).strokeColor(color));
        }
    }

}
