//
//  G00HomeVC.swift
//  dental
//
//  Created by SPJ on 1/9/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework

class G00HomeVC: BaseParentViewController {
    // MARK: Properties
    /** Logo */
    var imgLogo:        UIImageView = UIImageView()
    
    // MARK: Constant
    // Logo
    var LOGIN_LOGO_REAL_WIDTH_HD        = GlobalConst.LOGIN_LOGO_WIDTH * G00LoginExtVC.W_RATE_HD
    var LOGIN_LOGO_REAL_HEIGHT_HD       = GlobalConst.LOGIN_LOGO_HEIGHT * G00LoginExtVC.H_RATE_HD
    var LOGIN_LOGO_REAL_Y_POS_HD        = GlobalConst.LOGIN_LOGO_Y_POS * G00LoginExtVC.H_RATE_HD
    
    var LOGIN_LOGO_REAL_WIDTH_FHD       = GlobalConst.LOGIN_LOGO_WIDTH * G00LoginExtVC.W_RATE_FHD
    var LOGIN_LOGO_REAL_HEIGHT_FHD      = GlobalConst.LOGIN_LOGO_HEIGHT * G00LoginExtVC.H_RATE_FHD
    var LOGIN_LOGO_REAL_Y_POS_FHD       = GlobalConst.LOGIN_LOGO_Y_POS_FHD * G00LoginExtVC.H_RATE_FHD
    
    var LOGIN_LOGO_REAL_WIDTH_FHD_L     = GlobalConst.LOGIN_LOGO_WIDTH * G00LoginExtVC.W_RATE_FHD_L
    var LOGIN_LOGO_REAL_HEIGHT_FHD_L    = GlobalConst.LOGIN_LOGO_HEIGHT * G00LoginExtVC.H_RATE_FHD_L
    var LOGIN_LOGO_REAL_Y_POS_FHD_L     = GlobalConst.LOGIN_LOGO_Y_POS_FHD_LAND * G00LoginExtVC.H_RATE_FHD_L

    // MARK: Override methods
    /**
     * Called after the controller's view is loaded into memory.
     */
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        self.createNavigationBar(title: DomainConst.CONTENT00571)
        startLogic()
    }
    
    /**
     * Handle update constants
     */
    override func updateConst() {        
        // Login
        LOGIN_LOGO_REAL_WIDTH_HD        = GlobalConst.LOGIN_LOGO_WIDTH * G00LoginExtVC.W_RATE_HD
        LOGIN_LOGO_REAL_HEIGHT_HD       = GlobalConst.LOGIN_LOGO_HEIGHT * G00LoginExtVC.H_RATE_HD
        LOGIN_LOGO_REAL_Y_POS_HD        = GlobalConst.LOGIN_LOGO_Y_POS * G00LoginExtVC.H_RATE_HD
        
        LOGIN_LOGO_REAL_WIDTH_FHD       = GlobalConst.LOGIN_LOGO_WIDTH * G00LoginExtVC.W_RATE_FHD
        LOGIN_LOGO_REAL_HEIGHT_FHD      = GlobalConst.LOGIN_LOGO_HEIGHT * G00LoginExtVC.H_RATE_FHD
        LOGIN_LOGO_REAL_Y_POS_FHD       = GlobalConst.LOGIN_LOGO_Y_POS_FHD * G00LoginExtVC.H_RATE_FHD
        
        LOGIN_LOGO_REAL_WIDTH_FHD_L     = GlobalConst.LOGIN_LOGO_WIDTH * G00LoginExtVC.W_RATE_FHD_L
        LOGIN_LOGO_REAL_HEIGHT_FHD_L    = GlobalConst.LOGIN_LOGO_HEIGHT * G00LoginExtVC.H_RATE_FHD_L
        LOGIN_LOGO_REAL_Y_POS_FHD_L     = GlobalConst.LOGIN_LOGO_Y_POS_FHD_LAND * G00LoginExtVC.H_RATE_FHD_L
    }
    
    /**
     * Create children views
     */
    override func createChildrenViews() {
        super.createChildrenViews()
        // Get current device type
        switch UIDevice.current.userInterfaceIdiom {
        case .phone:        // iPhone
            self.createLogoImgHD()
            break
        case .pad:          // iPad
            switch UIApplication.shared.statusBarOrientation {
            case .portrait, .portraitUpsideDown:        // Portrait
                self.createLogoImgFHD()
            case .landscapeLeft, .landscapeRight:       // Landscape
                self.createLogoImgFHD_L()
            default:
                break
            }
            
            break
        default:
            break
        }
        
        self.view.addSubview(imgLogo)
    }
    
    /**
     * Update children views
     */
    override func updateChildrenViews() {
        super.updateChildrenViews()
        // Get current device type
        switch UIDevice.current.userInterfaceIdiom {
        case .phone:        // iPhone
            self.createLogoImgHD()
        case .pad:          // iPad
            switch UIApplication.shared.statusBarOrientation {
            case .portrait, .portraitUpsideDown:        // Portrait
                self.createLogoImgFHD()
            case .landscapeLeft, .landscapeRight:       // Landscape
                self.createLogoImgFHD_L()
            default:
                break
            }
            
            break
        default:
            break
        }
    }
    
    // MARK - Logo image
    /**
     * Create logo image
     * - parameter yPos: Y position
     * - parameter w:    Width
     * - parameter h:    Height
     */
    private func createLogoImg(yPos: CGFloat, w: CGFloat, h: CGFloat) {
        imgLogo.image = ImageManager.getImage(named: DomainConst.LOGO_LOGIN_ICON_IMG_NAME)
        imgLogo.contentMode = .scaleAspectFit
        
        CommonProcess.updateViewPos(view: imgLogo,
                                    x: (UIScreen.main.bounds.width - w) / 2,
                                    y: yPos,
                                    w: w,
                                    h: h)
    }
    
    /**
     * Create logo image (in HD mode)
     */
    private func createLogoImgHD() {
        createLogoImg(yPos: LOGIN_LOGO_REAL_Y_POS_HD,
                      w: LOGIN_LOGO_REAL_WIDTH_HD,
                      h: LOGIN_LOGO_REAL_HEIGHT_HD)
    }
    
    /**
     * Create logo image (in Full HD mode)
     */
    private func createLogoImgFHD() {
        createLogoImg(yPos: LOGIN_LOGO_REAL_Y_POS_FHD,
                      w: LOGIN_LOGO_REAL_WIDTH_FHD,
                      h: LOGIN_LOGO_REAL_HEIGHT_FHD)
    }
    
    /**
     * Create logo image (in Full HD Landscape mode)
     */
    private func createLogoImgFHD_L() {
        createLogoImg(yPos: LOGIN_LOGO_REAL_Y_POS_FHD_L,
                      w: LOGIN_LOGO_REAL_WIDTH_FHD_L,
                      h: LOGIN_LOGO_REAL_HEIGHT_FHD_L)
    }
    
    /**
     * Update logo image in HD mode
     */
    private func updateLogoImgHD() {
        CommonProcess.updateViewPos(
            view: imgLogo,
            x: (UIScreen.main.bounds.width - LOGIN_LOGO_REAL_WIDTH_HD) / 2,
            y: LOGIN_LOGO_REAL_Y_POS_HD,
            w: LOGIN_LOGO_REAL_WIDTH_HD,
            h: LOGIN_LOGO_REAL_HEIGHT_HD)
    }
    
    /**
     * Update logo image in Full HD mode
     */
    private func updateLogoImgFHD() {
        CommonProcess.updateViewPos(
            view: imgLogo,
            x: (UIScreen.main.bounds.width - LOGIN_LOGO_REAL_WIDTH_FHD) / 2,
            y: LOGIN_LOGO_REAL_Y_POS_FHD,
            w: LOGIN_LOGO_REAL_WIDTH_FHD,
            h: LOGIN_LOGO_REAL_HEIGHT_FHD)
    }
    
    /**
     * Update logo image in Full HD Landscape mode
     */
    private func updateLogoImgFHD_L() {
        CommonProcess.updateViewPos(
            view: imgLogo,
            x: (UIScreen.main.bounds.width - LOGIN_LOGO_REAL_WIDTH_FHD_L) / 2,
            y: LOGIN_LOGO_REAL_Y_POS_FHD_L,
            w: LOGIN_LOGO_REAL_WIDTH_FHD_L,
            h: LOGIN_LOGO_REAL_HEIGHT_FHD_L)
    }
    
    // MARK: Event handler
    internal func finishUpdateConfigRequest(_ notification: Notification) {
        let data = notification.object as! String
        let model = LoginRespBean(jsonString: data)
        if model.isSuccess() {
            LoginRespBean.saveConfigData(data: model)
        }
    }
    
    // MARK: Logic
    private func startLogic() {
        if BaseModel.shared.checkIsLogin() {
            requestUpdateConfig()
        } else {
            openLogin()
        }
    }
    
    private func requestUpdateConfig() {
        UpdateConfigurationRequest.requestUpdateConfiguration(
            action: #selector(finishUpdateConfigRequest(_:)),
            view: self)
    }

}
