function initializeTour(tourName){
	var thisTour = {};
	if(tourName===""){
		thisTour =  new Tour({
			  storage : false,
			  steps: [
			  {
			    orphan: true,
			    title: "Help not available!",
			    content: "Currently, There is no help available for this page.",
			    placement: "left"
			  }
			  ]
		});
	}
	if(tourName==="TOUR_PROFILE"){
		thisTour =  new Tour({
			  storage : false,
			  steps: [
			  {
			    element: "#menu-item-contact-us",
			    title: "Contact Us",
			    content: "Click here to visit your Contact Us page",
			    placement: "left"
			  },
			  {
			    element: "#menu-item-alias",
			    title: "Public URL",
			    content: "Share this URL with your network for direct link to your profile",
			    placement: "left"
			  },
			  {
			    element: "#menu-item-photos",
			    title: "Your photos",
			    content: "View your photos from here, You can Upload and add them to the profile slideshow.",
			    placement: "left"
			  },
			  {
			    element: "#parallax-window",
			    title: "Slideshow",
			    content: "As you add some photos to the slideshow from the photos page, they will start to appear here",
			    placement: "top",
			  },
			  {
			    element: "#photos-row",
			    title: "All the other photos",
			    content: "All your other photos appear here in this slider.",
			    placement: "top",
			    backdrop : true
			  },
			  {
			    element: "#menu-item-events",
			    title: "Your Events",
			    content: "View the list of events from here, you can create any upcoming event and showcase it on your profile.",
			    placement: "left"
			  },
			  {
			    element: "#events-row",
			    title: "Upcoming Events",
			    content: "All the upcoming events are showcased here in the slider",
			    placement: "bottom",
			    backdrop : true
			  },
			  {
			    element: "#btn-create-post",
			    title: "Create Post",
			    content: "You can create a Post by clicking here.",
			    placement: "left",
			    backdrop : true
			  },
			  {
			    element: ".grid-item:first",
			    title: "A Post",
			    content: "A post consists of a Title, Content and a Image. It could be anything you want to "+ 
			    " show on your profile.",
			    placement: "top",
			    backdrop : true
			  },
			  {
			    element: ".grid-item:last",
			    title: "Another example",
			    content: "You can also pin a post to keep it on top always, or they will appear as per the date created.",
			    placement: "top",
			    backdrop : true
			  },
			  {
			    element: "#img-logout",
			    title: "By the way!!",
			    content: 'Edit your profile, or logout by clicking on this dropdown',
			    placement: "bottom"
			  },
			]});
	}
	
	
	if(tourName==="TOUR_EVENT"){
		thisTour =  new Tour({
			  storage : false,
			  steps: [
			  {
			    element: "#event-menu",
			    title: "Event Menu",
			    content: "Operate your event menu from here:<br>"+
			    "1. Add some photos about or from the event,<br>"+
			    "2. Edit the event details, date-time-venue etc.. (This moves the Event back to create state)<br>"+
			    "3. Delete the event, <br>"+
			    "4. View the applications received from prospective volunteers.",
			    placement: "top",
			    backdrop:true
			  },
			  {
			    element: "#events-row",
			    title: "Upcoming Events",
			    content: "All the upcoming events are showcased here in the slider",
			    placement: "bottom",
			    backdrop : true
			  },
			  {
			    element: ".fb-share-button",
			    title: "Share this event on Facebook",
			    content: "Click here to share this event on Facebook and get even more popularity!",
			    placement: "right",
			    backdrop : true
			  },
			  {
			    element: "#event-photos-row",
			    title: "Event photos",
			    content: "All of the event photos appear in this slider.  Click any one to view in full-screen.",
			    placement: "top",
			    backdrop : true
			  },
			  {
			    element: "#volun-row",
			    title: "Volunteer Section",
			    content: "Once you move the event to Open state, you will be asked to fill Work Requirements for the event, so that any "+
			    "willing volunteer can come here and apply. ",
			    placement: "top",
			    backdrop : true
			  },
			  {
			    element: "#acc-app",
			    title: "Volunteers for this event",
			    content: "All the Accepted Volunteer Applications will appear here in this slider. ",
			    placement: "top",
			    backdrop : true
			  },
			  {
			    element: "#disqus_thread",
			    title: "Comments on the Event",
			    content: "Any more information that you want to provide on the event, can be done in this section. ",
			    placement: "top",
			    backdrop : true
			  }
			]});
	}
	
	
	if(tourName==="TOUR_EVENT_STATES"){
		thisTour =  new Tour({
			  storage : false,
			  steps: [
			  {
			    element: "#state-create",
			    title: "Event State - CREATE",
			    content: "This is the initial state of event after its creation. "+
						  "Volunteer won't be able to apply for this event unless you promote it to Open state."+
						  "<p style=\"color:red; font-style: italic;\">	Editing the event rollbacks the state to \"Create\".</p>",
			    placement: "bottom",
			    backdrop:true
			  },
			  {
			    element: "#state-open",
			    title: "Event State - OPEN",
			    content: "Event will be open in this state for volunteers to apply. "+
				  "When you are done with reviewing and accepting/rejecting the applications received, you can change the state to \"Closed\" state. "+
				  "<p style=\"color:red; font-style: italic;\">	If you do not want any external volunteer applications, you can change the state of event to \"Closed\".</p>",
			    placement: "bottom",
			    backdrop:true
			  },
			  {
			    element: "#state-closed",
			    title: "Event State - CLOSED",
			    content: "This state is reached 1 day after the event date to mark it closed. "+
				  "Volunteer won't be able to apply for this event unless you promote it to Open state."+
				  "<p style=\"color:red; font-style: italic;\">	Auto promote to this state will happen a day after the event date.</p>",
			    placement: "bottom",
			    backdrop:true
			  },
			  ]
		});
	}
	
	
	thisTour.addStep({
						element: "#wc-feedback",
					    title: "Any Feedback or Help!!",
					    content: "For any other doubts, Go to the Feedback page and post your Questions in the comments, <br> we will get back to you as soon as possible. ",
					    placement: "right",
					    backdrop: true
	    	});
	thisTour.init();
	return thisTour;
	
}