//
//  AppDelegate.swift
//  dental
//
//  Created by SPJ on 1/9/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework
let app = UIApplication.shared.delegate as! AppDelegate

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
    var HUD: MBProgressHUD?
    internal var rootNav: UINavigationController = UINavigationController()


    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        SlideMenuOptions.leftViewWidth = GlobalConst.POPOVER_WIDTH
        SlideMenuOptions.panGesturesEnabled = true
        SlideMenuOptions.hideStatusBar = false
        let firstVC = G00HomeVC(nibName: G00HomeVC.theClassName,
                                bundle: nil)
        rootNav = UINavigationController(rootViewController: firstVC)
        rootNav.isNavigationBarHidden = false
        let menu = MenuVC(nibName: MenuVC.theClassName, bundle: nil)
        let slide = BaseSlideMenuViewController(
            mainViewController: rootNav,
            leftMenuViewController: menu)
        
        slide.delegate = firstVC
        self.window?.rootViewController = slide
        self.window?.makeKeyAndVisible()
        return true
    }

    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }
    
    /**
     * Asks the delegate for the interface orientations to use for the view controllers in the specified window.
     * Only allow portrait for iPhone, allow all orientation for iPad
     */
    func application(_ application: UIApplication, supportedInterfaceOrientationsFor window: UIWindow?) -> UIInterfaceOrientationMask {
        switch UIDevice.current.userInterfaceIdiom {
        case .phone:
            return UIInterfaceOrientationMask.portrait
        case .pad:
            return UIInterfaceOrientationMask.all
        default:
            break
        }
        return UIInterfaceOrientationMask.all
    }
    
    //MARK: - App Function
    //MARK - HUD
    func showHUD(title: String){
        weak var weakself = self
        DispatchQueue.main.async {
            if (weakself?.HUD == nil) {
                weakself?.HUD = MBProgressHUD.showAdded(to: self.window!, animated: true)
                weakself?.HUD?.backgroundView.style = MBProgressHUDBackgroundStyle.solidColor
                weakself?.HUD?.backgroundView.color = UIColor.init(white: 0, alpha: 0.3)
                weakself?.HUD?.label.text = title
            }
        }
    }
    func closeHUD(){
        weak var weakself = self
        DispatchQueue.main.async {
            if(weakself?.HUD != nil){
                weakself?.HUD?.removeFromSuperview()
                weakself?.HUD = nil
            }
        }
    }

}

