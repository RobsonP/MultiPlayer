/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soSSH;

import instance.Instance_data;
import instance.Instance_hold;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;

/**
 *
 * @author RobsonP
 */
public class MPlayer_EventListener implements MediaPlayerEventListener{
    private long time = 0;
    private boolean trchange = false;
    
    @Override
    public void mediaChanged(MediaPlayer mp) {
        System.out.println("MEDIA: " + mp.getTrackInfo().get(0));
    }

    @Override
    public void opening(MediaPlayer mp) {
        Instance_hold.getMframe().getjProgressBar_play_visual().setIndeterminate(true);
    }

    @Override
    public void buffering(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void playing(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void paused(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void stopped(MediaPlayer mp) {
        Instance_hold.getMframe().getjProgressBar_play_visual().setIndeterminate(false);
    }

    @Override
    public void forward(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void backward(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void finished(MediaPlayer mp) {
        Instance_hold.getVplay_mon().setFinished(true);
        Instance_hold.getMframe().getjProgressBar_play_visual().setIndeterminate(false);
    }

    @Override
    public void timeChanged(MediaPlayer mp, long l) {
        time = l;
        String timestr = "";
        double timebuf = l;
        int min;
        int sec;
        
        timebuf = timebuf / 1000.0;
        min = (int)(timebuf / 60);
        sec = (int)((((timebuf / 60) - (int)(timebuf / 60))*100.0)*0.6);
        if (sec<10) timestr = min + ":0" + sec;
        else timestr = min + ":" + sec;
 
        if (timestr.length() == 4) {
            Instance_hold.getMframe().getjLabel_time().setText("   " + timestr);
            Instance_hold.getPlayframe().getjLabel_time().setText("   " + timestr);
            Instance_hold.getFsnt().getjLabel_time().setText("   " + timestr);
        }
        else if (timestr.length() == 5) {
            Instance_hold.getMframe().getjLabel_time().setText("  " + timestr);
            Instance_hold.getPlayframe().getjLabel_time().setText("  " + timestr);
            Instance_hold.getFsnt().getjLabel_time().setText("  " + timestr);
        }
        else if (timestr.length() == 6) {
            Instance_hold.getMframe().getjLabel_time().setText(" " + timestr);
            Instance_hold.getPlayframe().getjLabel_time().setText(" " + timestr);
            Instance_hold.getFsnt().getjLabel_time().setText(" " + timestr);
        }
        else {
            Instance_hold.getMframe().getjLabel_time().setText(timestr);
            Instance_hold.getPlayframe().getjLabel_time().setText(timestr);
            Instance_hold.getFsnt().getjLabel_time().setText(timestr);
        }
        
    }

    @Override
    public void positionChanged(MediaPlayer mp, float f) {        
        if (!trchange) {
            Instance_hold.getPlayframe().getjSlider_Media().setValue((int)(f*1000));
            Instance_hold.getFsnt().getjSlider_Media().setValue((int)(f*1000));
            Instance_hold.getMframe().getjSlider_Media().setValue((int)(f*1000));
            //System.out.println("POSITION: " + f);
            //System.out.println("DESCRIPTION: " + playframe.getEmpc().getMediaPlayer().getMediaDetails().getVideoTrackCount());
            //System.out.println("LENGTH: " + playframe.getEmpc().getMediaPlayer().getLength());
            //System.out.println("LENGTH: " + (timebuf*(1/f)));
            Instance_data.setMedia_length((long)(time*(1/f)));
            //System.out.println("MEDIALENGTH: " + Instance_data.getMedia_length());
            //System.out.println("DESCRIPTION: " + playframe.getEmpc().getMediaPlayer().getMediaDetails().getTitleDescriptions().get(0).description());
            if (Instance_data.isTrackchange()) {
                Instance_hold.getMframe().getjTable_playlist().repaint();
                Instance_data.setTrackchange(false);
            }   
        }
    }

    @Override
    public void seekableChanged(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void pausableChanged(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void titleChanged(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void snapshotTaken(MediaPlayer mp, String string) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void lengthChanged(MediaPlayer mp, long l) {
        String timestr = "";
        double time = l;
        int min;
        int sec;
        
        time = time / 1000.0;
        min = (int)(time / 60);
        sec = (int)((((time / 60) - (int)(time / 60))*100.0)*0.6);
        if (sec<10) timestr = min + ":0" + sec;
        else timestr = min + ":" + sec;
        
        Instance_hold.getMframe().getjTable_playlist().setValueAt(timestr, Instance_data.getCurrentplay(), 1);
    }

    @Override
    public void error(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mediaMetaChanged(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mediaSubItemAdded(MediaPlayer mp, libvlc_media_t l) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mediaDurationChanged(MediaPlayer mp, long l) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mediaParsedChanged(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mediaFreed(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mediaStateChanged(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void newMedia(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void subItemPlayed(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void subItemFinished(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void endOfSubItems(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }   

    public boolean isTrchange() {
        return trchange;
    }

    public void setTrchange(boolean trchange) {
        this.trchange = trchange;
    }  
}