package pucp.telecom.moviles.lab3.otro;

import android.media.MediaRecorder;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.ArrayList;

public class MedicionViewModel extends ViewModel {

    private MediaRecorder mRecorder;
    private double amplitude;
    private MutableLiveData<Integer> duracion;
    private MutableLiveData<Double> noise;
    private Thread thread = null;

    public void iniciarMedicion(){
        prepareRecorder();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int contador = 0;
                mRecorder.start();
                while(true){
                    contador++;
                    amplitude = 20 * Math.log10(mRecorder.getMaxAmplitude() / 2700.0);
                    try{
                        thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        mRecorder.stop();
                        mRecorder.release();
                        mRecorder = null;
                        break;
                    }
                    noise.postValue(amplitude);

                }
                duracion.postValue(contador);
            }
        });
        thread.start();
    }

    private void prepareRecorder(){
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile("/dev/null");
        try {
            mRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public MutableLiveData<Integer> getDuracion() {
        return duracion;
    }

    public MutableLiveData<Double> getNoise() {
        return noise;
    }
}
