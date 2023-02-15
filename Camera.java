public class Camera {

    public static Vector VRV = new Vector(1, 0, 0);
    public static Vector VUV = new Vector(0, 1, 0);
    public static Vector VRP = new Vector(0 ,0, -600);
    public static Vector VPN;

    public static Vector getVPN(){
        VPN = VRV.cross(VUV);
        VPN.normalise();
        return VPN;
    }

    public static Vector getVRP(Vector a, double distance){
        VRP = a.sub(VPN.mul(distance));
        return VRP;
    }

   

    public static void rotateVRV(double degree){
        double x = (VRV.x * Math.cos(Math.toRadians(degree))) - (VRV.z * Math.sin(Math.toRadians(degree)));
        double z = (VRV.x * Math.sin(Math.toRadians(degree))) + (VRV.z * Math.cos(Math.toRadians(degree)));
        VRV.x = x;
        VRV.z = z;
        VRV.normalise();
    }

    public static void rotateVUV(double degree){
        double y = (VUV.y * Math.cos(Math.toRadians(degree))) - (VUV.z * Math.sin(Math.toRadians(degree)));
        double z = (VUV.y * Math.sin(Math.toRadians(degree))) + (VUV.z * Math.cos(Math.toRadians(degree)));
        VUV.y = y;
        VUV.z = z;
        VUV.normalise();
    }

    public static Vector getCamOrigin(int w, int h){

        return (VRP.sub(VRV.mul(w))).sub(VUV.mul(h));
    }


    
}
