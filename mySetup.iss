; -- Example1.iss --
; Demonstrates copying 3 files and creating an icon.

; SEE THE DOCUMENTATION FOR DETAILS ON CREATING .ISS SCRIPT FILES!

[Setup]
AppName=SoSSH
AppVersion=2.4
DefaultDirName=C:\Program Files (x86)\SoSSH
DefaultGroupName=SoSSH
UninstallDisplayIcon={app}\SoSSH.exe
Compression=lzma2
SolidCompression=yes
OutputDir=userdocs:Inno Setup Examples Output

[Files]
Source: "SoSSH.exe"; DestDir: "{app}";
Source: "Readme.txt"; DestDir: "{app}"; Flags: isreadme

Source: "lib\jna-3.3.0-platform.jar"; DestDir: "{app}\lib";
Source: "lib\jna-3.3.0.jar"; DestDir: "{app}\lib";
Source: "lib\jsch-0.1.54.jar"; DestDir: "{app}\lib";
Source: "lib\vlcj-1.2.1-b2.jar"; DestDir: "{app}\lib";

Source: "src\gui\design\logo_startup.jpg"; DestDir: "{app}\gui\design";
Source: "src\gui\design\logo_small.jpg"; DestDir: "{app}\gui\design";

Source: "VLC\libvlc.dll"; DestDir: "{app}\VLC";
Source: "VLC\libvlccore.dll"; DestDir: "{app}\VLC";

Source: "VLC\plugins\3dnow\libmemcpy3dn_plugin.dll"; DestDir: "{app}\VLC\plugins\3dnow";

Source: "VLC\plugins\access\libaccess_attachment_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libaccess_bd_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libaccess_ftp_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libaccess_http_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libaccess_imem_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libaccess_mms_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libaccess_rar_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libaccess_realrtsp_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libaccess_smb_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libaccess_tcp_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libaccess_udp_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libaccess_vdr_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libcdda_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libdshow_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libdtv_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libdvdnav_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libdvdread_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libfilesystem_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libidummy_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\liblibbluray_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\librtp_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libscreen_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libsdp_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libstream_filter_rar_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libvcd_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
Source: "VLC\plugins\access\libzip_plugin.dll"; DestDir: "{app}\VLC\plugins\access";
                                                                                  
Source: "VLC\plugins\access_output\libaccess_output_dummy_plugin.dll"; DestDir: "{app}\VLC\plugins\access_output";
Source: "VLC\plugins\access_output\libaccess_output_file_plugin.dll"; DestDir: "{app}\VLC\plugins\access_output";
Source: "VLC\plugins\access_output\libaccess_output_http_plugin.dll"; DestDir: "{app}\VLC\plugins\access_output";
Source: "VLC\plugins\access_output\libaccess_output_livehttp_plugin.dll"; DestDir: "{app}\VLC\plugins\access_output";
Source: "VLC\plugins\access_output\libaccess_output_shout_plugin.dll"; DestDir: "{app}\VLC\plugins\access_output";
Source: "VLC\plugins\access_output\libaccess_output_udp_plugin.dll"; DestDir: "{app}\VLC\plugins\access_output";

Source: "VLC\plugins\audio_filter\liba52tofloat32_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\liba52tospdif_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libaudiobargraph_a_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libaudio_format_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libchorus_flanger_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libcompressor_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libconverter_fixed_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libdolby_surround_decoder_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libdtstofloat32_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libdtstospdif_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libequalizer_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libheadphone_channel_mixer_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libkaraoke_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libmono_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libmpgatofixed32_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libnormvol_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libparam_eq_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libsamplerate_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libscaletempo_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libsimple_channel_mixer_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libspatializer_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libspeex_resampler_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libtrivial_channel_mixer_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";
Source: "VLC\plugins\audio_filter\libugly_resampler_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_filter";

Source: "VLC\plugins\audio_mixer\libfixed32_mixer_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_mixer";
Source: "VLC\plugins\audio_mixer\libfloat32_mixer_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_mixer";


Source: "VLC\plugins\audio_output\libadummy_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_output";
Source: "VLC\plugins\audio_output\libamem_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_output";
Source: "VLC\plugins\audio_output\libaout_directx_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_output";
Source: "VLC\plugins\audio_output\libaout_file_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_output";
Source: "VLC\plugins\audio_output\libwaveout_plugin.dll"; DestDir: "{app}\VLC\plugins\audio_output";

Source: "VLC\plugins\codec\liba52_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libadpcm_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libaes3_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libaraw_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libavcodec_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libcc_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libcdg_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libcrystalhd_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libcvdsub_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libddummy_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libdmo_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libdts_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libdvbsub_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libedummy_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libfaad_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libflac_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libfluidsynth_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libkate_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\liblibass_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\liblibmpeg2_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\liblpcm_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libmpeg_audio_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libopus_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libpng_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libquicktime_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\librawvideo_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libschroedinger_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libspeex_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libspudec_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libstl_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libsubsdec_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libsubsusf_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libsvcdsub_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libt140_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libtheora_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libtwolame_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libvorbis_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libx264_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";
Source: "VLC\plugins\codec\libzvbi_plugin.dll"; DestDir: "{app}\VLC\plugins\codec";

Source: "VLC\plugins\control\libdummy_plugin.dll"; DestDir: "{app}\VLC\plugins\control";
Source: "VLC\plugins\control\libgestures_plugin.dll"; DestDir: "{app}\VLC\plugins\control";
Source: "VLC\plugins\control\libglobalhotkeys_plugin.dll"; DestDir: "{app}\VLC\plugins\control";
Source: "VLC\plugins\control\libhotkeys_plugin.dll"; DestDir: "{app}\VLC\plugins\control";
Source: "VLC\plugins\control\libnetsync_plugin.dll"; DestDir: "{app}\VLC\plugins\control";
Source: "VLC\plugins\control\libntservice_plugin.dll"; DestDir: "{app}\VLC\plugins\control";
Source: "VLC\plugins\control\liboldrc_plugin.dll"; DestDir: "{app}\VLC\plugins\control";

Source: "VLC\plugins\demux\libaiff_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libasf_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libau_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libavi_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libdemuxdump_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libdemux_cdg_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libdemux_stl_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libdirac_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libes_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libflacsys_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libgme_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libh264_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libimage_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\liblive555_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libmjpeg_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libmkv_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libmod_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libmp4_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libmpc_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libmpgv_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libnsc_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libnsv_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libnuv_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libogg_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libplaylist_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libps_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libpva_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\librawaud_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\librawdv_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\librawvid_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libreal_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libsid_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libsmf_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libsubtitle_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libts_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libtta_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libty_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libvc1_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libvobsub_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libvoc_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libwav_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";
Source: "VLC\plugins\demux\libxa_plugin.dll"; DestDir: "{app}\VLC\plugins\demux";

Source: "VLC\plugins\gui\libqt4_plugin.dll"; DestDir: "{app}\VLC\plugins\gui";
Source: "VLC\plugins\gui\libskins2_plugin.dll"; DestDir: "{app}\VLC\plugins\gui";

Source: "VLC\plugins\lua\liblua_plugin.dll"; DestDir: "{app}\VLC\plugins\lua";

Source: "VLC\plugins\meta_engine\libfolder_plugin.dll"; DestDir: "{app}\VLC\plugins\meta_engine";
Source: "VLC\plugins\meta_engine\libtaglib_plugin.dll"; DestDir: "{app}\VLC\plugins\meta_engine";

Source: "VLC\plugins\misc\libaudioscrobbler_plugin.dll"; DestDir: "{app}\VLC\plugins\misc";
Source: "VLC\plugins\misc\libexport_plugin.dll"; DestDir: "{app}\VLC\plugins\misc";
Source: "VLC\plugins\misc\libgnutls_plugin.dll"; DestDir: "{app}\VLC\plugins\misc";
Source: "VLC\plugins\misc\liblogger_plugin.dll"; DestDir: "{app}\VLC\plugins\misc";
Source: "VLC\plugins\misc\libmemcpy_plugin.dll"; DestDir: "{app}\VLC\plugins\misc";
Source: "VLC\plugins\misc\libosd_parser_plugin.dll"; DestDir: "{app}\VLC\plugins\misc";
Source: "VLC\plugins\misc\libstats_plugin.dll"; DestDir: "{app}\VLC\plugins\misc";
Source: "VLC\plugins\misc\libvod_rtsp_plugin.dll"; DestDir: "{app}\VLC\plugins\misc";
Source: "VLC\plugins\misc\libxml_plugin.dll"; DestDir: "{app}\VLC\plugins\misc";

Source: "VLC\plugins\mmx\libi420_rgb_mmx_plugin.dll"; DestDir: "{app}\VLC\plugins\mmx";
Source: "VLC\plugins\mmx\libi420_yuy2_mmx_plugin.dll"; DestDir: "{app}\VLC\plugins\mmx";
Source: "VLC\plugins\mmx\libi422_yuy2_mmx_plugin.dll"; DestDir: "{app}\VLC\plugins\mmx";
Source: "VLC\plugins\mmx\libmemcpymmx_plugin.dll"; DestDir: "{app}\VLC\plugins\mmx";

Source: "VLC\plugins\mmxext\libmemcpymmxext_plugin.dll"; DestDir: "{app}\VLC\plugins\mmxext";

Source: "VLC\plugins\mux\libmux_asf_plugin.dll"; DestDir: "{app}\VLC\plugins\mux";
Source: "VLC\plugins\mux\libmux_avi_plugin.dll"; DestDir: "{app}\VLC\plugins\mux";
Source: "VLC\plugins\mux\libmux_dummy_plugin.dll"; DestDir: "{app}\VLC\plugins\mux";
Source: "VLC\plugins\mux\libmux_mp4_plugin.dll"; DestDir: "{app}\VLC\plugins\mux";
Source: "VLC\plugins\mux\libmux_mpjpeg_plugin.dll"; DestDir: "{app}\VLC\plugins\mux";
Source: "VLC\plugins\mux\libmux_ogg_plugin.dll"; DestDir: "{app}\VLC\plugins\mux";
Source: "VLC\plugins\mux\libmux_ps_plugin.dll"; DestDir: "{app}\VLC\plugins\mux";
Source: "VLC\plugins\mux\libmux_ts_plugin.dll"; DestDir: "{app}\VLC\plugins\mux";
Source: "VLC\plugins\mux\libmux_wav_plugin.dll"; DestDir: "{app}\VLC\plugins\mux";

Source: "VLC\plugins\notify\libmsn_plugin.dll"; DestDir: "{app}\VLC\plugins\notify";

Source: "VLC\plugins\packetizer\libpacketizer_copy_plugin.dll"; DestDir: "{app}\VLC\plugins\packetizer";
Source: "VLC\plugins\packetizer\libpacketizer_dirac_plugin.dll"; DestDir: "{app}\VLC\plugins\packetizer";
Source: "VLC\plugins\packetizer\libpacketizer_flac_plugin.dll"; DestDir: "{app}\VLC\plugins\packetizer";
Source: "VLC\plugins\packetizer\libpacketizer_h264_plugin.dll"; DestDir: "{app}\VLC\plugins\packetizer";
Source: "VLC\plugins\packetizer\libpacketizer_mlp_plugin.dll"; DestDir: "{app}\VLC\plugins\packetizer";
Source: "VLC\plugins\packetizer\libpacketizer_mpeg4audio_plugin.dll"; DestDir: "{app}\VLC\plugins\packetizer";
Source: "VLC\plugins\packetizer\libpacketizer_mpeg4video_plugin.dll"; DestDir: "{app}\VLC\plugins\packetizer";
Source: "VLC\plugins\packetizer\libpacketizer_mpegvideo_plugin.dll"; DestDir: "{app}\VLC\plugins\packetizer";
Source: "VLC\plugins\packetizer\libpacketizer_vc1_plugin.dll"; DestDir: "{app}\VLC\plugins\packetizer";

Source: "VLC\plugins\plugins.dat"; DestDir: "{app}\VLC\plugins";

Source: "VLC\plugins\services_discovery\libmediadirs_plugin.dll"; DestDir: "{app}\VLC\plugins\services_discovery";
Source: "VLC\plugins\services_discovery\libpodcast_plugin.dll"; DestDir: "{app}\VLC\plugins\services_discovery";
Source: "VLC\plugins\services_discovery\libsap_plugin.dll"; DestDir: "{app}\VLC\plugins\services_discovery";
Source: "VLC\plugins\services_discovery\libupnp_plugin.dll"; DestDir: "{app}\VLC\plugins\services_discovery";
Source: "VLC\plugins\services_discovery\libwindrive_plugin.dll"; DestDir: "{app}\VLC\plugins\services_discovery";

Source: "VLC\plugins\sse2\libi420_rgb_sse2_plugin.dll"; DestDir: "{app}\VLC\plugins\sse2";
Source: "VLC\plugins\sse2\libi420_yuy2_sse2_plugin.dll"; DestDir: "{app}\VLC\plugins\sse2";
Source: "VLC\plugins\sse2\libi422_yuy2_sse2_plugin.dll"; DestDir: "{app}\VLC\plugins\sse2";

Source: "VLC\plugins\stream_filter\libstream_filter_dash_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_filter";
Source: "VLC\plugins\stream_filter\libstream_filter_httplive_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_filter";
Source: "VLC\plugins\stream_filter\libstream_filter_record_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_filter";

Source: "VLC\plugins\stream_out\libstream_out_autodel_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_bridge_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_delay_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_description_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_display_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_dummy_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_duplicate_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_es_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_gather_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_langfromtelx_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_mosaic_bridge_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_raop_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_record_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_rtp_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_select_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_setid_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_smem_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_standard_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";
Source: "VLC\plugins\stream_out\libstream_out_transcode_plugin.dll"; DestDir: "{app}\VLC\plugins\stream_out";

Source: "VLC\plugins\text_renderer\libfreetype_plugin.dll"; DestDir: "{app}\VLC\plugins\text_renderer";
Source: "VLC\plugins\text_renderer\libtdummy_plugin.dll"; DestDir: "{app}\VLC\plugins\text_renderer";

Source: "VLC\plugins\video_chroma\libgrey_yuv_plugin.dll"; DestDir: "{app}\VLC\plugins\video_chroma";
Source: "VLC\plugins\video_chroma\libi420_rgb_plugin.dll"; DestDir: "{app}\VLC\plugins\video_chroma";
Source: "VLC\plugins\video_chroma\libi420_yuy2_plugin.dll"; DestDir: "{app}\VLC\plugins\video_chroma";
Source: "VLC\plugins\video_chroma\libi422_i420_plugin.dll"; DestDir: "{app}\VLC\plugins\video_chroma";
Source: "VLC\plugins\video_chroma\libi422_yuy2_plugin.dll"; DestDir: "{app}\VLC\plugins\video_chroma";
Source: "VLC\plugins\video_chroma\librv32_plugin.dll"; DestDir: "{app}\VLC\plugins\video_chroma";
Source: "VLC\plugins\video_chroma\libyuy2_i420_plugin.dll"; DestDir: "{app}\VLC\plugins\video_chroma";
Source: "VLC\plugins\video_chroma\libyuy2_i422_plugin.dll"; DestDir: "{app}\VLC\plugins\video_chroma";

Source: "VLC\plugins\video_filter\libadjust_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libalphamask_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libantiflicker_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libatmo_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libaudiobargraph_v_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libball_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libblendbench_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libblend_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libbluescreen_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libcanvas_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libchain_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libclone_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libcolorthres_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libcroppadd_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libdeinterlace_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\liberase_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libextract_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libgaussianblur_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libgradfun_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libgradient_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libgrain_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libhqdn3d_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libinvert_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\liblogo_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libmagnify_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libmarq_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libmirror_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libmosaic_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libmotionblur_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libmotiondetect_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libosdmenu_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libpanoramix_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libposterize_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libpostproc_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libpsychedelic_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libpuzzle_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libremoteosd_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libripple_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\librotate_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\librss_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libscale_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libscene_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libsepia_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libsharpen_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libsubsdelay_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libswscale_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libtransform_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libwall_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libwave_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";
Source: "VLC\plugins\video_filter\libyuvp_plugin.dll"; DestDir: "{app}\VLC\plugins\video_filter";

Source: "VLC\plugins\video_output\libcaca_plugin.dll"; DestDir: "{app}\VLC\plugins\video_output";
Source: "VLC\plugins\video_output\libdirect2d_plugin.dll"; DestDir: "{app}\VLC\plugins\video_output";
Source: "VLC\plugins\video_output\libdirect3d_plugin.dll"; DestDir: "{app}\VLC\plugins\video_output";
Source: "VLC\plugins\video_output\libdirectx_plugin.dll"; DestDir: "{app}\VLC\plugins\video_output";
Source: "VLC\plugins\video_output\libdrawable_plugin.dll"; DestDir: "{app}\VLC\plugins\video_output";
Source: "VLC\plugins\video_output\libglwin32_plugin.dll"; DestDir: "{app}\VLC\plugins\video_output";
Source: "VLC\plugins\video_output\libvdummy_plugin.dll"; DestDir: "{app}\VLC\plugins\video_output";
Source: "VLC\plugins\video_output\libvmem_plugin.dll"; DestDir: "{app}\VLC\plugins\video_output";
Source: "VLC\plugins\video_output\libwingdi_plugin.dll"; DestDir: "{app}\VLC\plugins\video_output";
Source: "VLC\plugins\video_output\libyuv_plugin.dll"; DestDir: "{app}\VLC\plugins\video_output";

Source: "VLC\plugins\visualization\libgoom_plugin.dll"; DestDir: "{app}\VLC\plugins\visualization";
Source: "VLC\plugins\visualization\libprojectm_plugin.dll"; DestDir: "{app}\VLC\plugins\visualization";
Source: "VLC\plugins\visualization\libvisual_plugin.dll"; DestDir: "{app}\VLC\plugins\visualization";


[Icons]
Name: "{group}\SoSSH"; Filename: "{app}\SoSSH.exe"
