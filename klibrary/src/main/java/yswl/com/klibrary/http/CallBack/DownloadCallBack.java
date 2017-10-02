package yswl.com.klibrary.http.CallBack;

/**
     * 下载状态回调 
     */  
    public interface DownloadCallBack {  
        /** 
         * 开始下载（获取文件成功） 
         * 
         * @param fileLength 文件长度 
         */  
        public void onStart(long fileLength);  
  
        /** 
         * 下载中(将文件保存到本地) 
         * 
         * @param fileLength 文件长度 
         * @param progress   已下载长度 
         */  
        public void onGoing(long fileLength, long downloadLength);  
  
        /** 
         * 下载失败 
         * 
         * @param errorException 错误原因 
         */  
        public void onFilure(String exception);  
    }  